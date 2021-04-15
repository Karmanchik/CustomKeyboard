package house.with.swimmingpool.ui.news.listnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.api.config.controllers.NewsServiceImpl
import house.with.swimmingpool.databinding.FragmentNewsListBinding
import house.with.swimmingpool.models.NewsData
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.home.adapters.NewsAdapter
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.news.singlenews.NewsSingleFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {

    private var newsListBinding: FragmentNewsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsListBinding = FragmentNewsListBinding.inflate(layoutInflater)
        return newsListBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListBinding?.apply {
            backIcon.setOnClickListener {
                back()
            }

            GlobalScope.launch(Dispatchers.IO) {
                val newsData = NewsServiceImpl().loadNews()
                launch(Dispatchers.Main){
                    if (newsData.first != null) {
                        showNewsList(newsData.first!!.toMutableList())
                    }
                }
            }
        }
        load() { Log.e("test", "") }
    }

    private fun showNewsList(newsList: MutableList<NewsData>) {
        newsListBinding?.newsRV?.apply {
            adapter = NewsAdapter(newsList.map { it as Any }.toMutableList().apply {
                try {
                    add(6, "big")
                } catch (e: Exception) {
                    Log.e("testNewsBannerException", e.toString())
                }
            }, requireContext()) {
                val bundle = Bundle().apply {
                    putInt("id", it.id ?: 0)
                }
                navigate(NewsSingleFragment(), bundle)
            }
        }
        newsListBinding?.apply {
            header.visibility = View.VISIBLE
            body.visibility = View.VISIBLE
            loader.visibility = View.GONE
        }
    }

    fun load(onClick: () -> Unit) {
        onClick.invoke()
    }

    override fun onDestroy() {
        newsListBinding = null
        super.onDestroy()
    }

}