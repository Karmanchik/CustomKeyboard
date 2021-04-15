package house.with.swimmingpool.ui.news.singlenews

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import house.with.swimmingpool.api.config.controllers.NewsServiceImpl
import house.with.swimmingpool.databinding.FragmentNewsSingleBinding
import house.with.swimmingpool.models.SingleNewsData
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.home.adapters.MediaAdapter
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.house.adapters.ChildrenHouseAdapter
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.news.NewsTagAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsSingleFragment : Fragment() {

    private var singleNewsBinding: FragmentNewsSingleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        singleNewsBinding = FragmentNewsSingleBinding.inflate(layoutInflater)
        return singleNewsBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        singleNewsBinding?.apply {

            houseBackIcon.setOnClickListener {
                back()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            val dataNews = NewsServiceImpl().loadSingleNews(arguments?.getInt("id") ?: 0)

            launch(Dispatchers.Main) {
                showNews(dataNews)
            }
        }

    }

    private fun showNews(dataNews: Pair<SingleNewsData?, Throwable?>) {
        singleNewsBinding?.apply {
            if (dataNews.first != null && dataNews.second == null) {

                val data = dataNews.first!!

                headerTitleTextView.text = data.title

                titleTextView.text = data.title

                if (data.date != null && data.date.isNotEmpty()) {
                    dateTextView.text = data.date
                    setDateVisibility(View.VISIBLE)
                } else {
                    setDateVisibility(View.GONE)
                }

                if (data.reading_time != null && data.reading_time.isNotEmpty()) {
                    timeTextView.text = data.reading_time
                    setTimeVisibility(View.VISIBLE)
                } else {
                    setTimeVisibility(View.GONE)
                }

                val vp = housesImageContainerNews
                val vpAdapter = when {
                    data.photos != null && data.photos.isNotEmpty() -> {
                        Log.e("photos", data.photos.size.toString())
                        MediaAdapter(data.photos, listOf("-cYOlHknhBU"), requireContext())
                    }
                    data.icon != null -> {
                        Log.e("photos", data.icon.toString())
                        MediaAdapter(
                            listOf(data.icon),
                            listOf("-cYOlHknhBU"),
                            requireContext()
                        )
                    }
                    else -> {
                        MediaAdapter(listOf(""), listOf("-cYOlHknhBU"), requireContext())
                    }
                }
                vp.adapter = vpAdapter
                dotsIndicatorSingle.setViewPager2(vp)

                nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (housesImageContainerNews.bottom in (oldScrollY + 1) until scrollY) {
                        vp.adapter = vpAdapter
                        dotsIndicatorSingle.setViewPager2(vp)
                    }
                }

                if (data.tags != null && data.tags.isNotEmpty()) {
                    tegRecyclerView.adapter = NewsTagAdapter(requireContext(), data.tags)
                    setTagsVisibility(View.VISIBLE)
                } else {
                    setTagsVisibility(View.GONE)
                }

                if (data.introtext != null && data.introtext.isNotEmpty()) {
                    introText.text = Html.fromHtml(data.introtext)
                    introText.visibility = View.VISIBLE
                } else {
                    introText.visibility = View.GONE
                }

                Log.e("context", data.content.toString())
                if (data.content != null && data.content.isNotEmpty()) {
                    contentTextView.text = Html.fromHtml(data.content)
                    setContentVisibility(View.VISIBLE)
                } else {
                    setContentVisibility(View.GONE)
                }

                if (data.linked_objects != null && data.linked_objects.isNotEmpty()) {
                    setAnalogsVisibility(View.VISIBLE)
                    analogsRV.apply {
                        layoutManager = GridLayoutManager(context, 2)
                        adapter =
                            ChildrenHouseAdapter(data.linked_objects.take(2)) { homeId ->
                                val home =
                                    data.linked_objects.firstOrNull { it.id == homeId }
                                val bundle =
                                    Bundle().apply {
                                        putString(
                                            "home",
                                            Gson().toJson(home)
                                        )
                                    }
                                navigate(
                                    HouseFragment(),
                                    bundle
                                )
                            }
                    }
                }
            }
            headerLayout.visibility = View.VISIBLE
            nestedScrollView.visibility = View.VISIBLE
            loader.visibility = View.GONE
        }
    }

    private fun setTagsVisibility(visibility: Int) {
        singleNewsBinding?.apply {
            topTagDivider.visibility = visibility
            tegRecyclerView.visibility = visibility
            bottomTagDivider.visibility = visibility
        }
    }

    private fun setContentVisibility(visibility: Int) {
        singleNewsBinding?.apply {
            contentDivider.visibility = visibility
            contentTextView.visibility = visibility
        }
    }

    private fun setAnalogsVisibility(visibility: Int) {
        singleNewsBinding?.apply {
            analogsTextView.visibility = visibility
            analogsRV.visibility = visibility
        }
    }

    private fun setDateVisibility(visibility: Int) {
        singleNewsBinding?.apply {
            dateImageView.visibility = visibility
            dateTextView.visibility = visibility
        }
    }

    private fun setTimeVisibility(visibility: Int) {
        singleNewsBinding?.apply {
            timeImageView.visibility = visibility
            timeTextView.visibility = visibility
        }
    }

    override fun onDestroy() {
        singleNewsBinding = null
        super.onDestroy()
    }
}