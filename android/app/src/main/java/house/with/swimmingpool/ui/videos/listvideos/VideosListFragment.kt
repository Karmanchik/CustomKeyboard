package house.with.swimmingpool.ui.videos.listvideos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.VideosServiceImpl
import house.with.swimmingpool.databinding.FragmentVideoListBinding
import house.with.swimmingpool.models.VideosData
import house.with.swimmingpool.ui.home.adapters.VideosAdapter

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
                findNavController().popBackStack()
            }

            VideosServiceImpl().getVideos { data, e ->
                if(data != null && e == null) {

                    textViewCount.text = "${data.size} Видеообзоров"

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
                            val bundel = Bundle().apply {
                                putInt("id", it)
                            }
                            findNavController().navigate(R.id.action_videosListFragment_to_videoFragment, bundel)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        listVideoBinding = null
        super.onDestroy()
    }
}