package house.with.swimmingpool.ui.house

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter
import house.with.swimmingpool.ui.home.adapters.IViewHouse
import house.with.swimmingpool.ui.home.adapters.SeenHousesAdapter
import house.with.swimmingpool.ui.house.adapters.*

class HouseFragment : Fragment(), IViewHouse {

    private var houseObjectBinding: FragmentHouseBinding? = null

    private var map: GoogleMap? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        houseObjectBinding = FragmentHouseBinding.inflate(layoutInflater)

        return houseObjectBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val houseId = arguments?.getInt("house")

        houseObjectBinding?.apply {

            RealtyServiceImpl().getHouseExample(houseId ?: 0) { data, e ->
                if (data != null) {
                    whiteButtonGalleryRV.adapter =
                            MainGalleryAndDateAdapter(
                                    requireContext(),
                                    data.getGallery(),
                                    this@HouseFragment)

//                    data.getGallery()?.forEach {
//                        Log.e("tagss", it.second[0].name)
//                    }


                    price.text = data.price + " руб."
                    discount.text = data.discount.toString()

                    if (data.mainTags != null) {
                        hashTagRV.adapter = TagAdapter(requireContext(), data.mainTags)
                    }

                    setMainHouseContainer()

                    title.text = data.title
                    textViewLocation.text = data.location

                    val note = null


                    if (note == null) {
                        noteLayout.visibility = View.GONE
                    } else {
//                        noteText = note
                    }

                    description.text = data.description

//                    if (data.video.isNullOrEmpty()) {
                    youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            val videoId = "-cYOlHknhBU"//videos?.get(adapterPosition - items.size) ?: ""
                            youTubePlayer.loadVideo(videoId, 0f)
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
                        }
                    })
//                    }

                    if (data.type == "house" || data.type == "flat") {
                        housesListText.visibility = View.GONE
                        listHouseBox.visibility = View.GONE
                    }

                    if (data.type == "complex") {
                        housesListText.text = "Список квартир"
                    }


                    if (data.advantages != null) {
                        whiteButtonAdvantagesRV.adapter = AdvantagesAdapter(requireContext(), data.advantages)
                    } else {
                        whiteButtonAdvantagesRV.visibility = View.GONE
                    }

                }
            }

            whiteButtonRV.adapter = WhiteButtonAdapter(requireContext(), listOf("Общие", "Коммуникации", "Оформление", "Оплата"))

            listHouseBox.apply {
                showListHouse(false)
            }

            showListHouseBox.setOnClickListener {
                showListHouse(true)
            }

            collapseListHouseBox.setOnClickListener {
                showListHouse(false)
            }


//            mapView.onCreate(savedInstanceState?.getBundle())

            mapView.getMapAsync { googleMap ->
                map = googleMap
                val positions = LatLng(55.75222, 37.61556)

                googleMap.apply {
                    addMarker(MarkerOptions().position(positions).title("Marker in Sydney"))
                    moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 10f))
                }
            }

            RealtyServiceImpl().getHouseCatalog { data, e ->
                similarObjects.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = SeenHousesAdapter(requireContext(), listOf(data?.get(0), data?.get(1))) {
                        findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
                    }
                }
            }

            houseBackIcon.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun showListHouse(full: Boolean) {
        houseObjectBinding?.apply {
            if (full) {

                listHouseBox.apply {
                    showListHouseBox.visibility = View.GONE
                    collapseListHouseBox.visibility = View.VISIBLE
                    layoutManager = GridLayoutManager(context, 3)
                    adapter = ListHouseBoxAdapter(requireContext(), listOf("", "", "", "", "", "", "", "", "", "", "", "", ""))
                }
            } else {
                listHouseBox.apply {
                    showListHouseBox.visibility = View.VISIBLE
                    collapseListHouseBox.visibility = View.GONE
                    layoutManager = GridLayoutManager(context, 3)
                    adapter = ListHouseBoxAdapter(requireContext(), listOf("", "", "", "", "", ""))
                }
            }
        }
    }

    private fun setMainHouseContainer(
//            photos: List<String>
    ) {
        houseObjectBinding?.apply {
            val vp = mainHousesContainer
            vp.adapter = HouseHeaderAdapter(listOf("", "", ""))

            dotsIndicator.setViewPager2(vp)
        }
    }

    override fun onDestroy() {
        houseObjectBinding = null
        super.onDestroy()
    }

    override fun showHeaderGallery(list: ArrayList<String>) {
        houseObjectBinding?.apply {
            val vp = mainHousesContainer
            vp.adapter = HouseHeaderAdapter(list)

            dotsIndicator.setViewPager2(vp)
        }
    }
}