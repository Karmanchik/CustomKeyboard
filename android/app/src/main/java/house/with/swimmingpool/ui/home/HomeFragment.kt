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
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.House
import house.with.swimmingpool.models.News
import house.with.swimmingpool.models.Video
import house.with.swimmingpool.ui.filter.short.ShortFilterFragment
import house.with.swimmingpool.ui.home.adapters.*

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            videosRV.adapter = VideosAdapter(listOf(Video(), Video())) {
                findNavController().navigate(R.id.action_navigation_home_to_videoFragment)
            }
            newsRV.adapter = NewsAdapter(listOf(News(), News()), requireContext()) {
                findNavController().navigate(R.id.action_navigation_home_to_newsSingleFragment)
            }

            lastSeenRV.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = SeenHousesAdapter(listOf(House(), House(), House())) {
                    findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
                }
            }

            shortCatalogRV.adapter = CatalogAdapter(listOf(House(), House())) {
                findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
            }

            storiesRV.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = StoriesAdapter(listOf("", "", "", ""))
            }

            val vp = mainHousesContainer
            vp.adapter = HeaderAdapter(listOf(House(), House(), House())) {
                findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
            }

            dotsIndicator.setViewPager2(vp)

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
                    when(tab!!.position){
                        0->{nestedScrollView.smoothScrollTo(0, fullFilterView.bottom)}
                        1 -> {nestedScrollView.smoothScrollTo(0, adsLinear.bottom)}
                        2 -> {nestedScrollView.smoothScrollTo(0, divider.bottom)}
                        3 -> {nestedScrollView.smoothScrollTo(0, divider2.bottom)}
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0->{nestedScrollView.smoothScrollTo(0, textView16.top)}
                        1 -> {nestedScrollView.smoothScrollTo(0, textView.top)}
                        2 -> {nestedScrollView.smoothScrollTo(0, textView3.top)}
                        3 -> {nestedScrollView.smoothScrollTo(0, textView5.top)}
                    }
                }

            })

//            tabCatalog.setOnClickListener {nestedScrollView.smoothScrollTo(0, 0)  }

//            testButton.setOnClickListener { nestedScrollView.smoothScrollTo(0, 0) }

        }
    }
}