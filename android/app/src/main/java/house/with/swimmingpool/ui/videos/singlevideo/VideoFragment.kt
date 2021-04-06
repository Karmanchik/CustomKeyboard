package house.with.swimmingpool.ui.videos.singlevideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentVideoSingleBinding

class VideoFragment : Fragment(){

    private var videoBinding: FragmentVideoSingleBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(App.setting.user?.phone != ""){
            videoBinding?.phoneInputConsultation?.setText(App.setting.user?.phone)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        videoBinding = FragmentVideoSingleBinding.inflate(layoutInflater)
        return videoBinding?.root
    }

    override fun onDestroy() {
        videoBinding = null
        super.onDestroy()
    }
}