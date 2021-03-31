package house.with.swimmingpool.ui.videos.listvideos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listVideoBinding?.apply {
            backIcon.setOnClickListener {
                findNavController().popBackStack()
            }

            videosRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = VideosAdapter(true, requireContext(), listOf(
                        VideosData("date", 4, "", 0, "introText", "Title"),
                        VideosData("date", 4, "", 0, "introText", "Title"),
                        VideosData("date", 4, "", 0, "introText", "Title"),
                        VideosData("date", 4, "", 0, "introText", "Title"),
                        VideosData("date", 4, "", 0, "introText", "Title")
                )) {
                    findNavController().navigate(R.id.action_videosListFragment_to_videoFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        listVideoBinding = null
        super.onDestroy()
    }
}