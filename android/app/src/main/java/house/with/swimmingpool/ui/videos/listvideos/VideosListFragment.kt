package house.with.swimmingpool.ui.videos.listvideos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import house.with.swimmingpool.api.config.controllers.VideosServiceImpl
import house.with.swimmingpool.databinding.FragmentVideoListBinding
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.home.adapters.VideosAdapter
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.videos.singlevideo.VideoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideosListFragment : Fragment() {

    private var listVideoBinding: FragmentVideoListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listVideoBinding = FragmentVideoListBinding.inflate(layoutInflater)
        return listVideoBinding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listVideoBinding?.apply {
            backIcon.setOnClickListener {
                back()
            }

            GlobalScope.launch(Dispatchers.IO) {
                val videosData = VideosServiceImpl().loadVideos()

                launch(Dispatchers.Main) {
                    if (videosData.first != null && videosData.second == null) {
                        val data = videosData.first!!
                        videosRV.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = VideosAdapter(true, requireContext(),
                                data.map { it as Any }.toMutableList().apply {
                                    try {
                                        add(4, "big")
                                    } catch (e: Exception) {
                                        Log.e("testNewsBannerException", e.toString())
                                    }
                                }) {
                                val bundle = Bundle().apply {
                                    putInt("id", it)
                                }
                                navigate(VideoFragment(), bundle)
                            }
                        }
                    }
                    header.visibility = View.VISIBLE
                    videosRV.visibility = View.VISIBLE
                    loader.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        listVideoBinding = null
        super.onDestroy()
    }
}