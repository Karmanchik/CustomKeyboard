package house.with.swimmingpool.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.*
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.filter.short.ShortFilterFragment
import house.with.swimmingpool.ui.home.adapters.*
import house.with.swimmingpool.ui.search.SearchActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    companion object {
        const val NAVIGATE_TO_CATALOG = 1
        const val NAVIGATE_TO_OBJECT = 2
    }

    private var homeBinding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        return homeBinding?.root
    }

    override fun onDestroy() {
        homeBinding = null
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            data?.getIntExtra("action", 0)?.let { code ->
                if (code == NAVIGATE_TO_CATALOG) {
                    findNavController().navigate(R.id.action_navigation_home_to_catalogViewModel)
                } else if (code == NAVIGATE_TO_OBJECT) {
                    val bundle =
                        Bundle().apply { putString("home", Gson().toJson(App.setting.tmpObj)) }
                    findNavController().navigate(
                        R.id.action_navigation_home_to_houseFragment,
                        bundle
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var houseCatalogData: List<HouseCatalogData>? = null

        homeBinding?.apply {

            imageViewSearch.setOnClickListener {
                startActivityForResult(Intent(requireContext(), SearchActivity::class.java), 0)
            }

            VideosServiceImpl().getVideos { data, e ->
                if (e == null && data != null)
                    videosRV.adapter = VideosAdapter(false, requireContext(), data) {
                        findNavController().navigate(R.id.action_navigation_home_to_videoFragment)
                    }
            }

            NewsServiceImpl().getNews { data, e ->
                if (e == null && data != null) {
                    newsRV.adapter = NewsAdapter(
                        if (data.size > 2) listOf(data[0], data[1]) else data,
                        requireContext()
                    ) {
                        val bundle = Bundle().apply {
                            putInt("id", it.id ?: 0)
                            putSerializable("house", it)
                        }
                        findNavController().navigate(
                            R.id.action_navigation_home_to_newsSingleFragment,
                            bundle
                        )
                    }
                }
            }

            RealtyServiceImpl().getHouseCatalog { data, e ->
                lastSeenRV.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = SeenHousesAdapter(requireContext(), data ?: listOf()) { homeId ->
                        val home = data?.firstOrNull { it.id == homeId }
                        val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                        findNavController().navigate(
                            R.id.action_navigation_home_to_houseFragment,
                            bundle
                        )
                    }
                }
            }

            StoriesServiceImpl().getStories { data, e ->
                storiesRV.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    data?.let {
                        adapter = StoriesAdapter(it)
                    }
                }
            }

            BannersServiceImpl().getMainBanners { data, e ->
                if (e == null && data != null) {
                    val vp = mainHousesContainer
                    vp.adapter = HeaderAdapter(data)
                    { link ->

                        val splitLink = link.split("/")

                        when (splitLink.first().toString()) {
                            "objects" -> {
                                val home = App.setting.houses.firstOrNull {
                                    it.id == splitLink.last().toInt()
                                }
                                val bundle =
                                    Bundle().apply { putString("home", Gson().toJson(home)) }
                                findNavController().navigate(
                                    R.id.action_navigation_home_to_houseFragment,
                                    bundle
                                )
                            }
                            "news" -> {
                            }
                            "video" -> {
                            }
                            "https:" -> {
                                val browserIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        "http://www.google.com"
                                    )
                                )
                                startActivity(browserIntent);
                            }
                        }
//                        val home = App.setting.houses.firstOrNull { it.id == link }
//                        val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
//                        findNavController().navigate(R.id.action_navigation_home_to_houseFragment, bundle)
                    }
                    dotsIndicator.setViewPager2(vp)
                }
            }

            BannersServiceImpl().getBanners { data, e ->
                Glide.with(this@HomeFragment)
                    .load(data?.get(0)?.bigBanner)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(bigAdBanner)
                Glide.with(this@HomeFragment)
                    .load(data?.get(1)?.smallBanner)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(firstAdBanner)
                Glide.with(this@HomeFragment)
                    .load(data?.get(2)?.smallBanner)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(secondAdBanner)
            }

            nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (shortCatalogRV.bottom in (oldScrollY + 1) until scrollY) {
                    setShortCatalog(houseCatalogData)
                }

                if (textViewShortCatalog.top - coordinateLayout.height in (scrollY + 1) until oldScrollY) {
                    setShortCatalog(houseCatalogData)
                }
            }


            segmentedControl.addOnSegmentClickListener { svh ->
                RealtyServiceImpl().getHouseCatalog { data, e ->
                    if (e == null && data != null) {
                        houseCatalogData = when (svh.absolutePosition) {
                            0 -> {
                                data.filter { it.type == "house" || it.type == "village" }
                            }

                            1 -> {
                                data.filter { it.type == "flat" }
                            }

                            else -> {
                                data.filter { it.type == "complex" }
                            }
                        }
                        setShortCatalog(houseCatalogData)
                    }
                }
            }
            segmentedControl.setSelectedSegment(0)


            shortFilterView.setOnClickListener {
                val onClick =
                    { findNavController().navigate(R.id.action_navigation_home_to_catalogViewModel) }
                ShortFilterFragment(onClick).newInstance(onClick).show(
                    parentFragmentManager, "shortFilter"
                )
            }
            fullFilterView.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_fullFilterFragment)
            }

            showCatalogButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_catalogViewModel)
            }
            showVideosButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_videosListFragment)
            }
            showNewsButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_newsListFragment)
            }



            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    moveOnTabSelected(tab!!.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    moveOnTabSelected(tab!!.position)
                }
            })

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setShortCatalog(data: List<HouseCatalogData>?) {
        homeBinding?.apply {
            if (data != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    App.setting.houses = data
                }

                textViewVideosCount.text = "${data.size} предложений"
                shortCatalogRV.adapter =
                    CatalogAdapter(data.take(2), requireContext()) { homeId ->
                        val home = data.firstOrNull { it.id == homeId }
                        val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                        findNavController().navigate(
                            R.id.action_navigation_home_to_houseFragment,
                            bundle
                        )
                    }

            }
        }
    }

    private fun moveOnTabSelected(position: Int) {
        homeBinding?.apply {
            when (position) {
                0 -> {
                    nestedScrollView.smoothScrollTo(0, fullFilterView.bottom, 1500)
                }
                1 -> {
                    nestedScrollView.smoothScrollTo(0, adsLinear.bottom, 1500)
                }
                2 -> {
                    nestedScrollView.smoothScrollTo(0, divider.bottom, 1500)
                }
                3 -> {
                    nestedScrollView.smoothScrollTo(0, divider2.bottom, 1500)
                }
            }
        }
    }
}