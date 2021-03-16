package house.with.swimmingpool.ui.videos.listvideos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R

class VideosListFragment : Fragment(R.layout.fragment_video_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.backIcon).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<RecyclerView>(R.id.videosRV).apply {
            layoutManager = LinearLayoutManager(context)
//            adapter = VideosAdapter(listOf(Video(), Video(), Video())) {
//                findNavController().navigate(R.id.action_videosListFragment_to_videoFragment)
//            }
        }
    }

}