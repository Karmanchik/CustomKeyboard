package house.with.swimmingpool.ui.news.singlenews

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.NewsServiceImpl
import house.with.swimmingpool.databinding.FragmentNewsSingleBinding
import house.with.swimmingpool.ui.home.adapters.MediaAdapter
import house.with.swimmingpool.ui.house.adapters.ChildrenHouseAdapter
import house.with.swimmingpool.ui.news.NewsTagAdapter

class NewsSingleFragment : Fragment() {

    private var singleNewsBinding: FragmentNewsSingleBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        singleNewsBinding = FragmentNewsSingleBinding.inflate(layoutInflater)
        return singleNewsBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        singleNewsBinding?.apply {

            houseBackIcon.setOnClickListener {
                findNavController().popBackStack()
            }

            NewsServiceImpl().getSingleNews(3481) { data, e ->
                val vp = housesImageContainerNews
                if (data != null) {

                    headerTitleTextView.text = data.title

                    titleTextView.text = data.title

                    if (data.date != null && data.date.isNotEmpty()) {
                        dateTextView.text = data.date
                       setDateVisibility(View.VISIBLE)
                    }else{
                        setDateVisibility(View.GONE)
                    }

                    if (data.reading_time != null && data.reading_time.isNotEmpty()){
                        timeTextView.text = data.reading_time
                        setTimeVisibility(View.VISIBLE)
                    }else{
                        setTimeVisibility(View.GONE)
                    }

                    vp.adapter = when {
                        data.photos != null && data.photos.isNotEmpty() -> {
                            Log.e("photos", data.photos.size.toString())
                            MediaAdapter(data.photos, listOf("-cYOlHknhBU"), requireContext())
                        }
                        data.icon != null -> {
                            Log.e("photos", data.icon.toString())
                            MediaAdapter(listOf(data.icon), listOf("-cYOlHknhBU"), requireContext())
                        }
                        else -> {
                            MediaAdapter(listOf(""), listOf("-cYOlHknhBU"), requireContext())
                        }
                    }
                    dotsIndicatorSingle.setViewPager2(vp)

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
                            adapter = if (data.linked_objects.size > 1) {
                                ChildrenHouseAdapter(requireContext(), listOf(data.linked_objects[0], data.linked_objects[1])) { homeId ->
                                    val home = App.setting.houses.firstOrNull { it.id == homeId }
                                    val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                                    findNavController().navigate(R.id.action_newsSingleFragment_to_houseFragment, bundle)
                                }
                            } else {
                                ChildrenHouseAdapter(requireContext(), data.linked_objects) { homeId ->
                                    val home = App.setting.houses.firstOrNull { it.id == homeId }
                                    val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                                    findNavController().navigate(R.id.action_newsSingleFragment_to_houseFragment, bundle)
                                }
                            }
                        }
                    } else {
                        setContentVisibility(View.GONE)
                    }
                }
            }
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

    private fun setDateVisibility(visibility: Int){
        singleNewsBinding?.apply {
            dateImageView.visibility = visibility
            dateTextView.visibility =  visibility
        }
    }

    private fun setTimeVisibility(visibility: Int){
        singleNewsBinding?.apply {
            timeImageView.visibility = visibility
            timeTextView.visibility =  visibility
        }
    }
    override fun onDestroy() {
        singleNewsBinding = null
        super.onDestroy()
    }
}