package house.with.swimmingpool.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import house.with.swimmingpool.R
import house.with.swimmingpool.models.House
import house.with.swimmingpool.models.News
import house.with.swimmingpool.models.Video
import house.with.swimmingpool.ui.home.adapters.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.videosRV).adapter =
                VideosAdapter(listOf(Video(), Video()))

        view.findViewById<RecyclerView>(R.id.newsRV).adapter =
                NewsAdapter(listOf(News(), News()))

        view.findViewById<RecyclerView>(R.id.lastSeenRV).apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = SeenHousesAdapter(listOf(House(), House(), House()))
        }

        view.findViewById<RecyclerView>(R.id.shortCatalogRV).adapter =
                CatalogAdapter(listOf(House(), House()))

        view.findViewById<RecyclerView>(R.id.storiesRV).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = StoriesAdapter(listOf("", "", "", ""))
        }

        view.findViewById<ViewPager2>(R.id.mainHousesContainer).adapter =
                HeaderAdapter(listOf(House(), House(), House()))

    }

}