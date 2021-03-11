package house.with.swimmingpool.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.House
import house.with.swimmingpool.models.News
import house.with.swimmingpool.models.Video
import house.with.swimmingpool.ui.home.adapters.*

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            videosRV.adapter = VideosAdapter(listOf(Video(), Video()))
            newsRV.adapter = NewsAdapter(listOf(News(), News()), requireContext())

            lastSeenRV.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = SeenHousesAdapter(listOf(House(), House(), House()))
            }

            shortCatalogRV.adapter = CatalogAdapter(listOf(House(), House()))

            storiesRV.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = StoriesAdapter(listOf("", "", "", ""))
            }

            val vp = mainHousesContainer
            vp.adapter = HeaderAdapter(listOf(House(), House(), House()))

            dotsIndicator.setViewPager2(vp)
        }
    }
}