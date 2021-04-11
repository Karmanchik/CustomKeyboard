package house.with.swimmingpool.ui.videos.singlevideo

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.api.config.controllers.VideosServiceImpl
import house.with.swimmingpool.databinding.FragmentVideoSingleBinding
import house.with.swimmingpool.models.SingleVideoData
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoFragment : Fragment() {

    private var videoBinding: FragmentVideoSingleBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoBinding?.apply {

            loader.visibility = View.VISIBLE

            videoBackIcon.setOnClickListener { back() }

            GlobalScope.launch(Dispatchers.IO) {
                var dataVideo = VideosServiceImpl().loadSingleVideos(arguments?.getInt("id") ?: 0)
//                var exceptionVideo: Throwable? = null
//                VideosServiceImpl().getSingleVideo(arguments?.getInt("id") ?: 0) { data, e ->
//                    dataVideo = data
//                    exceptionVideo = e
//
//                    Log.e("testingCorutins", "data = $data  e = $e")
//                }
                Log.e("testingCorutins", "start")
                launch(Dispatchers.Main) {
                    Log.e("testingCorutins", "second")
                    if (dataVideo.first != null && dataVideo.second == null) {
                        Log.e("testingCorutins", "3")
                        moveToObject.setOnClickListener {

                            val home = App.setting.houses.firstOrNull {
                                it.id == id
                            }

                            if (home == null) {
                                RealtyServiceImpl().getHouseExample(
                                        dataVideo.first?.linked_objects ?: 0
                                ) { data, e, error ->
                                    Log.e("OkH", "data $data exception $error")
                                    if (error == 751) {
                                        Toast.makeText(
                                                requireContext(),
                                                "Объект с таким ID не найден",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (error == null) {
                                        val bundle =
                                                Bundle().apply { putString("home", Gson().toJson(data)) }

                                        navigate(HouseFragment(), bundle)
                                    }
                                }
                            } else {
                                val bundle =
                                        Bundle().apply { putString("home", Gson().toJson(home)) }
                                navigate(HouseFragment(), bundle)
                            }
                        }

                        val descriptionText =
                                if (dataVideo.first?.content != null) Html.fromHtml(dataVideo.first?.content) else ""

                        textViewTitle.text = dataVideo.first?.title

                        textViewIntroText.text = dataVideo.first?.introtext

                        if (dataVideo.first?.content?.trim()
                                        .isNullOrEmpty() || descriptionText.isEmpty()
                        ) {
                            contentTextView.visibility = View.GONE
                            textViewAboutObject.visibility = View.GONE
                        } else {
                            contentTextView.visibility = View.VISIBLE
                            textViewAboutObject.visibility = View.VISIBLE
                            contentTextView.text = Html.fromHtml(dataVideo.first?.content)
                        }

                        if (dataVideo.first?.date != "") {
                            dateTextView.text = dataVideo.first?.date
                            dateTextView.visibility = View.VISIBLE
                        } else {
                            dateTextView.visibility = View.GONE
                        }

                        Glide.with(requireContext())
                                .load(dataVideo.first?.icon)
                                .error(R.drawable.error_placeholder_midle)
                                .placeholder(R.drawable.placeholder)
                                .into(imageViewVideoPreloader)

                        if (!dataVideo.first?.video.isNullOrEmpty()) {
                            videoLayout.visibility = View.VISIBLE
                            youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                                    val videoId =
//                                        "-cYOlHknhBU"//
                                            dataVideo.first?.video
                                    youTubePlayer.loadVideo(videoId ?: "", 0f)
                                    youTubePlayer.pause()

                                    youTubePlayerView.minimumHeight = imageViewVideoPreloader.height

                                    imageViewVideoPreloader.setOnClickListener {
                                        it.visibility = View.INVISIBLE
                                        relativeLayout.visibility = View.INVISIBLE
                                        youTubePlayer.play()
                                        youTubePlayerView.enterFullScreen()
                                        youTubePlayerView.exitFullScreen()
                                        youTubePlayerView.enterFullScreen()
                                    }

                                    nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                                        if (videoLayout.bottom in (oldScrollY + 1) until scrollY) {
                                            youTubePlayer.pause()
                                        }

                                        if (videoLayout.top - videoLayout.height in (scrollY + 1) until oldScrollY) {
                                            youTubePlayer.pause()
                                        }
                                    }
                                }
                            })
                            headerLayout.visibility = View.VISIBLE
                            nestedScrollView.visibility = View.VISIBLE
                            loader.visibility = View.GONE

                        } else {
                            videoLayout.visibility = View.GONE
                        }
                    }
                }
            }
        }

        if (App.setting.user?.phone != "") {
            videoBinding?.phoneInputConsultation?.setText(App.setting.user?.phone)
        }

        if (App.setting.user?.phone != "") {
            videoBinding?.textInputEmail?.setText(App.setting.user?.email)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        videoBinding = FragmentVideoSingleBinding.inflate(layoutInflater)
        return videoBinding?.root
    }

    override fun onDestroy() {
        videoBinding = null
        super.onDestroy()
    }
}