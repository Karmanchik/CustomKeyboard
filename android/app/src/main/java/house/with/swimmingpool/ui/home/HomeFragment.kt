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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.*
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.filter.short.ShortFilterFragment
import house.with.swimmingpool.ui.home.adapters.*
import house.with.swimmingpool.ui.load
import house.with.swimmingpool.ui.popups.PopupActivity
import house.with.swimmingpool.ui.search.SearchActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    companion object {
        const val NAVIGATE_TO_CATALOG = 1
        const val NAVIGATE_TO_OBJECT = 2
        const val POPUP_WIFI_ERROR_REFRASH = 201
    }

    private var homeBinding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
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

        if(requestCode == POPUP_WIFI_ERROR_REFRASH){
            updateData()
        }
    }

    private fun updateData() {

        homeBinding?.loader?.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val videosInfo = VideosServiceImpl().loadVideos()
            val newsInfo = NewsServiceImpl().loadNews()
            val storiesInfo = StoriesServiceImpl().loadStories()
            val headerInfo = BannersServiceImpl().loadHeader()
            val ads = BannersServiceImpl().loadBanners()

            launch(Dispatchers.Main) {

                if (videosInfo.second != null
                        && newsInfo.second != null
                        && storiesInfo.second != null
                        && headerInfo.second != null
                        && ads.second != null) {
                    startActivityForResult(
                            Intent(requireContext(), PopupActivity::class.java).apply {
                                putExtra(App.TYPE_OF_POPUP, App.INTERNET_ERROR)
                            },
                            POPUP_WIFI_ERROR_REFRASH
                    )
                    homeBinding?.nestedScrollView?.visibility = View.GONE
                } else {
                    homeBinding?.nestedScrollView?.visibility = View.VISIBLE
                }


                homeBinding?.loader?.visibility = View.GONE

                homeBinding?.storiesRV?.apply {
                    visibility = if (storiesInfo.first.isNullOrEmpty()) View.GONE else View.VISIBLE
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = StoriesAdapter(storiesInfo.first ?: listOf())
                }

                homeBinding?.nestedScrollView?.visibility =
                    if (headerInfo.first.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }

                homeBinding?.apply {
                    val vp = mainHousesContainer
                    vp.adapter = HeaderAdapter(headerInfo.first ?: listOf())
                    { link ->

                        val splitLink = link.split("/")

                        when (splitLink.first().toString()) {
                            "objects" -> {
                                val home = App.setting.houses.firstOrNull {
                                    it.id == splitLink.last().toInt()
                                }

                                if(home == null){
                                    RealtyServiceImpl().getHouseExample(splitLink.last().toInt()){ data, e ->
                                        val bundle =
                                                Bundle().apply { putString("home", Gson().toJson(data)) }
                                        findNavController().navigate(
                                                R.id.action_navigation_home_to_houseFragment,
                                                bundle
                                        )
                                    }
                                }else {
                                    val bundle =
                                            Bundle().apply { putString("home", Gson().toJson(home)) }
                                    findNavController().navigate(
                                            R.id.action_navigation_home_to_houseFragment,
                                            bundle
                                    )
                                }
                            }
                            "news" -> {
                                val bundle = Bundle().apply {
                                    putInt("id", splitLink.last().toInt())
                                }
                                findNavController().navigate(
                                        R.id.action_navigation_home_to_newsSingleFragment,
                                        bundle
                                )
                            }
                            "video" -> {
                                val bundel = Bundle().apply{
                                    putInt("id", splitLink.last().toInt())
                                }
                                findNavController().navigate(
                                        R.id.action_navigation_home_to_videoFragment, bundel
                                )
                            }
                            "https:" -> {
                                val browserIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        link
                                    )
                                )
                                startActivity(browserIntent);
                            }
                        }

                        Log.e("mainBannersTestLink", splitLink.toString())
                    }
                    dotsIndicator.setViewPager2(vp)

                }

                if (!ads.first.isNullOrEmpty()) {
                    homeBinding?.apply {
                        bigAdBanner.load(ads.first?.get(0)?.bigBanner)
                        firstAdBanner.load(ads.first?.get(1)?.smallBanner)
                        secondAdBanner.load(ads.first?.get(2)?.smallBanner)

                        bigBanner.visibility = View.VISIBLE
                        adsLinear.visibility = View.VISIBLE
                    }
                } else {
                    homeBinding?.bigBanner?.visibility = View.VISIBLE
                    homeBinding?.adsLinear?.visibility = View.VISIBLE
                }

                if (videosInfo.second == null && videosInfo.first != null) {
                    homeBinding?.videosRV?.adapter =
                        VideosAdapter(false, requireContext(), videosInfo.first ?: listOf()) {
                            val bundel = Bundle().apply{
                                putInt("id", it)
                            }
                            findNavController().navigate(
                                    R.id.action_navigation_home_to_videoFragment, bundel
                            )
                        }
                    homeBinding?.videosContainer?.visibility = View.VISIBLE
                } else {
                    homeBinding?.videosContainer?.visibility = View.GONE
                }

                if (newsInfo.second == null && newsInfo.first != null) {
                    homeBinding?.newsRV?.adapter = NewsAdapter(
                        newsInfo.first?.take(2) ?: listOf(),
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
                    homeBinding?.newsContainer?.visibility = View.VISIBLE
                } else {
                    homeBinding?.newsContainer?.visibility = View.GONE
                }
            }
        }

//            RealtyServiceImpl().getHouseCatalog { data, e ->
//                lastSeenRV.apply {
//                    layoutManager = GridLayoutManager(context, 2)
//                    adapter = SeenHousesAdapter(requireContext(), data ?: listOf()) { homeId ->
//                        val home = data?.firstOrNull { it.id == homeId }
//                        val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
//                        findNavController().navigate(
//                            R.id.action_navigation_home_to_houseFragment,
//                            bundle
//                        )
//                    }
//                }
//            }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var houseCatalogData: List<HouseCatalogData>? = null

        homeBinding?.apply {

            imageViewSearch.setOnClickListener {
                startActivityForResult(Intent(requireContext(), SearchActivity::class.java), 0)
            }

            updateData()

            nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
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
                App.setting.filterConfig = null
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

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

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
            val pos = when (position) {
                0 -> fullFilterView.bottom
                1 -> adsLinear.bottom
                2 -> videosContainer.top
                3 -> newsContainer.top
                else -> null
            }
            pos?.let { nestedScrollView.smoothScrollTo(0, it, 1500) }
        }
    }
}