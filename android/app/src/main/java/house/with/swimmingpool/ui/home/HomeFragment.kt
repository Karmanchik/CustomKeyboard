package house.with.swimmingpool.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
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
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.*
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.House
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.filter.short.ShortFilterFragment
import house.with.swimmingpool.ui.home.adapters.*

class HomeFragment : Fragment() {

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding?.apply {

            VideosServiceImpl().getVideos { data, e ->
                if (e == null && data != null)
                    videosRV.adapter = VideosAdapter(requireContext(), data) {
                        findNavController().navigate(R.id.action_navigation_home_to_videoFragment)
                    }
            }

            NewsServiceImpl().getNews { data, e ->
                if (e == null && data != null) {
                    newsRV.adapter = NewsAdapter(data, requireContext()) {
                        val bundle = Bundle().apply {
                            putSerializable("house", it)
                        }
                        findNavController().navigate(R.id.action_navigation_home_to_newsSingleFragment)
                    }
                }
            }

            RealtyServiceImpl().getHouseCatalog { data, e ->
                lastSeenRV.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = SeenHousesAdapter(requireContext(), data ?: listOf(null)) {
                        findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
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
                    vp.adapter = HeaderAdapter(data) {
                        val bundle = Bundle().apply {
                            putString("id", "dsfsdfsd")
                        }
                        findNavController().navigate(R.id.action_navigation_home_to_houseFragment, bundle)
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

            segmentedControl.addOnSegmentClickListener { svh ->
                RealtyServiceImpl().getHouseCatalog { data, e ->
                    if (e == null && data != null) {
                        setShortCatalog(

                                     when (svh.absolutePosition) {
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
                        )
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
    private fun setShortCatalog(data: List<HouseCatalogData>) {
        homeBinding?.apply {
            textViewVideosCount.text = "${data.size}  предложений"
            shortCatalogRV.adapter = if (data.size > 2) {
                CatalogAdapter(listOf(data[0], data[1]), requireContext()) {
                    val bundle = Bundle().apply {
                        putSerializable("house", it)
                    }
                    findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
                }
            } else {
                CatalogAdapter(data, requireContext()) {
                    findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
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