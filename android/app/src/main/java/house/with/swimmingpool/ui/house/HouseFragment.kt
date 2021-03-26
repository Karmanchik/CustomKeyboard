package house.with.swimmingpool.ui.house

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.HouseExampleData
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter
import house.with.swimmingpool.ui.home.adapters.SeenHousesAdapter
import house.with.swimmingpool.ui.house.adapters.*
import house.with.swimmingpool.ui.house.interfaces.ISingleHouseView

class HouseFragment : Fragment(), ISingleHouseView {

    private var houseObjectBinding: FragmentHouseBinding? = null

    private var map: GoogleMap? = null

    private var houseExampleData: HouseExampleData? = null

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
                    houseExampleData = data

                    data.getGallery().apply {
                        whiteButtonGalleryRV.adapter =
                                MainGalleryAndDateAdapter(
                                        requireContext(),
                                        this,
                                        this@HouseFragment)

                        val galleryNameList: ArrayList<String> = arrayListOf()
                        var listImage: ArrayList<String> = arrayListOf()

                        if (galleryNameList.size == 0) {
                            whiteButtonGalleryRV.visibility = View.GONE
                        }

                        if (!this.isNullOrEmpty()) {
                            this.first().second.forEach { img ->
                                listImage.add(img.url)
                            }

                            this.forEach {
                                galleryNameList.add(it.first)
                            }
                        }
                        showHeaderGallery(listImage)
                    }

                    price.text = data.price + " руб."
                    discount.text = data.discount.toString()
                    title.text = data.title
                    textViewLocation.text = data.location
                    hitsText.text = data.hits.toString()

                    if (data.mainTags != null) {
                        hashTagRV.adapter = TagAdapter(requireContext(), data.mainTags)
                    }

                    val note = null
                    if (note == null) {
                        noteLayout.visibility = View.GONE
                    } else {
//                        noteText = note
                    }

                    whiteButtonRV.adapter = WhiteButtonAdapter(requireContext(), this@HouseFragment, listOf("Общие", "Коммуникации", "Оформление", "Оплата"))

                    showInformation(0)

                    if (data.description.isNullOrEmpty()) {
                        description.visibility = View.GONE
                    } else {
                        description.text = data.description
                    }

//                    if (data.video.isNullOrEmpty()) {

                    Glide.with(requireContext())
//                            .load("https://i.ytimg.com/vi/${videos?.get(position)}/maxresdefault.jpg")
                            .load("https://i.ytimg.com/vi/-cYOlHknhBU/maxresdefault.jpg")
                            .error(R.drawable.error_placeholder_midle)
                            .placeholder(R.drawable.placeholder)
                            .into(imageViewVideoPreloader)

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

                    listHouseBox.apply {
                        showListHouse(false)
                    }

                    showListHouseBox.setOnClickListener {
                        showListHouse(true)
                    }

                    collapseListHouseBox.setOnClickListener {
                        showListHouse(false)
                    }

                }
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
                        val bundle = Bundle().apply {
                            putSerializable("house", it)
                        }
                        findNavController().navigate(R.id.action_houseFragment_self, bundle)
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
            if (houseExampleData != null) {
                if (full) {
                    listHouseBox.apply {
                        showListHouseBox.visibility = View.GONE
                        collapseListHouseBox.visibility = View.VISIBLE
                        layoutManager = GridLayoutManager(context, 3)
                        adapter = ListHouseBoxAdapter(requireContext(), houseExampleData?.children)
                    }
                } else {
                    listHouseBox.apply {
                        showListHouseBox.visibility = View.VISIBLE
                        collapseListHouseBox.visibility = View.GONE
                        layoutManager = GridLayoutManager(context, 3)
                        if ((houseExampleData?.children?.size) ?: 0 < 6) {
                            adapter = ListHouseBoxAdapter(requireContext(), houseExampleData?.children)
                        } else {
                            houseExampleData?.apply {
                                adapter = ListHouseBoxAdapter(
                                        requireContext(),
                                        listOf(
                                                children?.get(0), children?.get(1), children?.get(2),
                                                children?.get(3), children?.get(4), children?.get(5),
                                        )
                                )
                            }
                        }
                    }
                }
            } else {
                listHouseBox.visibility = View.GONE
                showListHouseBox.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        houseObjectBinding = null
        super.onDestroy()
    }

    override fun showHeaderGallery(list: ArrayList<String>) {
        houseObjectBinding?.apply {
            Log.e("testing", list.toString())
            val vp = mainHousesContainer
            vp.adapter = HouseHeaderAdapter(list)

            dotsIndicator.setViewPager2(vp)
        }
    }

    override fun showInformation(position: Int) {
        if (houseExampleData != null) {
            val fragment = when (position) {
                0 -> {
                    InformationFragment(
                            houseExampleData?.formattedGeneral()
                    )
                }

                1 -> {
                    InformationFragment(
                            houseExampleData?.formattedCommunications()
                    )
                }

                2 -> {
                    InformationFragment(
                            houseExampleData?.formattedRegistration()
                    )
                }

                else -> {
                    InformationFragment(
                            houseExampleData?.formattedPayment()
                    )
                }
            }

            childFragmentManager.transaction {
                replace(R.id.informationFrame, fragment)
            }
        }
    }
}