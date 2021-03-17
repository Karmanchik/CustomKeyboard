package house.with.swimmingpool.ui.news.listnews

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.models.News
import house.with.swimmingpool.models.NewsData
import house.with.swimmingpool.ui.home.adapters.NewsAdapter

class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.newsRV).apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = NewsAdapter(listOf(NewsData.createEmpty(), NewsData.createEmpty()), requireContext()) {
                findNavController().navigate(R.id.action_newsListFragment_to_newsSingleFragment)
            }
        }

        load() { Log.e("test", "") }
    }

    fun load(onClick: () -> Unit) {
        onClick.invoke()
    }

}