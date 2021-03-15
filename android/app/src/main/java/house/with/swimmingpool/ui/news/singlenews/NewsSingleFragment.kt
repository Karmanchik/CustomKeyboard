package house.with.swimmingpool.ui.news.singlenews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import house.with.swimmingpool.R
import house.with.swimmingpool.models.House
import house.with.swimmingpool.models.News
import house.with.swimmingpool.ui.home.adapters.CatalogImageAdapter
import house.with.swimmingpool.ui.home.adapters.MediaAdapter
import house.with.swimmingpool.ui.home.adapters.NewsAdapter

class NewsSingleFragment : Fragment(R.layout.fragment_news_single) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vp = view.findViewById<ViewPager2>(R.id.contentMediaVP2)
        vp.adapter = MediaAdapter(listOf("House()", ""))

        view.findViewById<RecyclerView>(R.id.analogsRV).apply {
            layoutManager = GridLayoutManager(context, 2)
//            adapter = NewsAdapter(listOf(News(), News()), context) {}
        }
    }

}