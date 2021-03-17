package house.with.swimmingpool.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.NewsServiceImpl
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.api.config.controllers.StoriesServiceImpl
import house.with.swimmingpool.api.config.controllers.VideosServiceImpl
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.House
import house.with.swimmingpool.ui.filter.short.ShortFilterFragment
import house.with.swimmingpool.ui.home.adapters.*

class HomeFragment : Fragment() {

    lateinit var houmeBinding : FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        houmeBinding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        return houmeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        houmeBinding.apply {

            VideosServiceImpl().getVideos { data, e ->
                if(e == null && data != null)
                videosRV.adapter = VideosAdapter(requireContext(), data) {
                    findNavController().navigate(R.id.action_navigation_home_to_videoFragment)
                }
            }

            NewsServiceImpl().getNews { data, e ->
                if(e == null && data != null) {
                    newsRV.adapter = NewsAdapter(data, requireContext()) {
                        findNavController().navigate(R.id.action_navigation_home_to_newsSingleFragment)
                    }
                }
            }

            lastSeenRV.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = SeenHousesAdapter(listOf(House(), House(), House())) {
                    findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
                }
            }

            StoriesServiceImpl().getStories { data, e ->
                storiesRV.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    if(e == null && data != null) {
                        adapter = StoriesAdapter(requireContext(), data)
                    }
                }
            }

            val vp = mainHousesContainer
            vp.adapter = HeaderAdapter(listOf(House(), House(), House())) {

                findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
            }

            dotsIndicator.setViewPager2(vp)

            RealtyServiceImpl().getHouseCatalog { data, e ->
                if(e == null && data != null) {
                   shortCatalogRV.adapter = if(data.size > 2) {
                       CatalogAdapter(listOf(data[0], data[1]), requireContext()) {
                           findNavController().navigate(R.id.action_navigation_home_to_catalogViewModel)
                       }
                   }else {
                       CatalogAdapter(data, requireContext()) {
                           findNavController().navigate(R.id.action_navigation_home_to_catalogViewModel)
                       }
                   }
                }
            }

            segmentedControl.setSelectedSegment(0)

            shortFilterView.setOnClickListener {
                val onClick = { findNavController().navigate(R.id.action_navigation_home_to_catalogViewModel) }
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



            tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
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

    private fun moveOnTabSelected(position: Int) {
        houmeBinding.apply {
            when (position) {
                0->{nestedScrollView.smoothScrollTo(0, fullFilterView.bottom, 1500)}
                1 -> {nestedScrollView.smoothScrollTo(0, adsLinear.bottom, 1500)}
                2 -> {nestedScrollView.smoothScrollTo(0, divider.bottom, 1500)}
                3 -> {nestedScrollView.smoothScrollTo(0, divider2.bottom, 1500)}
            }
        }
    }
}