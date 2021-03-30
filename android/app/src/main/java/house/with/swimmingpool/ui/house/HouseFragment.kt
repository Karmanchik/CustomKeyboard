package house.with.swimmingpool.ui.house

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.models.HouseExampleData
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter
import house.with.swimmingpool.ui.favourites.liked.binding
import house.with.swimmingpool.ui.home.adapters.SeenHousesAdapter
import house.with.swimmingpool.ui.house.adapters.*
import house.with.swimmingpool.ui.house.interfaces.ISingleHouseView

class HouseFragment : Fragment(), ISingleHouseView {

    private var houseObjectBinding: FragmentHouseBinding? = null

    private var mapview: MapView? = null

    private var houseExampleData: HouseExampleData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        houseObjectBinding = FragmentHouseBinding.inflate(layoutInflater)

        return houseObjectBinding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        houseObjectBinding?.houseBackIcon?.setOnClickListener {
            findNavController().popBackStack()
        }

        val singleHouseObject =
            Gson().fromJson((arguments?.getString("home")), HouseExampleData::class.java)

        if(singleHouseObject != null) {
            showHome(singleHouseObject)

            RealtyServiceImpl().getHouseExample(
                    singleHouseObject.id ?: 0
            ) { data, e ->
                data?.let { showHome(it) }
            }
        }else{
            houseObjectBinding?.apply{
                price.visibility = View.INVISIBLE
                discount.visibility = View.INVISIBLE
                textViewLocation.visibility = View.INVISIBLE
                textViewForMonth.visibility = View.INVISIBLE
                noteLayout.visibility = View.GONE
                textViewAboutObject.visibility = View.GONE
                description.visibility = View.GONE
                videoLayout.visibility = View.GONE
                textViewAdvantages.visibility = View.GONE
                textViewSimilarObject.visibility = View.GONE
                textViewMoneyInMonth.visibility = View.INVISIBLE
                textViewLocationMap.visibility = View.GONE
                mapLayout.visibility = View.GONE
                housesListText.visibility = View.GONE
                showListHouseBox.visibility = View.GONE
                consultationLayout.visibility = View.GONE
                collBackLayout.visibility = View.GONE
                paperwork.visibility = View.GONE
                dividerFirst.visibility = View.GONE
                dividerSecond.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun showHome(singleHouseObject: HouseExampleData) {

        houseObjectBinding?.apply {

            houseExampleData = singleHouseObject

            singleHouseObject.getGallery().apply {
                whiteButtonGalleryRV.adapter =
                    MainGalleryAndDateAdapter(
                        requireContext(),
                        this,
                        this@HouseFragment
                    )

                val galleryNameList: ArrayList<String> = arrayListOf()
                val listImage: ArrayList<String> = arrayListOf()

                if (this.isNullOrEmpty() || this.size == 1) {
                    whiteButtonGalleryRV.visibility = View.GONE
                }else{
                    whiteButtonGalleryRV.visibility = View.VISIBLE
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

            price.text = singleHouseObject.price + " руб."
            if (singleHouseObject.priceHike != 0 && singleHouseObject.priceHike != null) {
                discount.text = "+${singleHouseObject.priceHike}%"
            } else {
                discount.visibility = View.INVISIBLE
                textViewForMonth.visibility = View.INVISIBLE
            }
            title.text = singleHouseObject.title
            textViewLocation.text = singleHouseObject.location
            hitsText.text = singleHouseObject.hits.toString()

            if (singleHouseObject.mainTags != null) {
                hashTagRV.adapter = TagAdapter(requireContext(), singleHouseObject.mainTags)
            }

            val note = null
            if (note == null) {
                noteLayout.visibility = View.GONE
            } else {
//                        noteText = note
                noteLayout.visibility = View.VISIBLE
            }

            whiteButtonRV.adapter = WhiteButtonAdapter(
                requireContext(), this@HouseFragment, listOf(
                    "Общие",
                    "Коммуникации",
                    "Оформление",
                    "Оплата"
                )
            )

            showInformation(0)

            val descriptionText = Html.fromHtml(singleHouseObject.description)

            if (singleHouseObject.description?.trim().isNullOrEmpty() || descriptionText.isEmpty()) {
                description.visibility = View.GONE
                textViewAboutObject.visibility = View.GONE
                dividerFirst.visibility = View.GONE
                dividerSecond.visibility = View.GONE
                videoLayout.visibility = View.GONE
            } else {
                videoLayout.visibility = View.VISIBLE
                description.visibility = View.VISIBLE
                textViewAboutObject.visibility = View.VISIBLE
                dividerFirst.visibility = View.VISIBLE
                dividerSecond.visibility = View.VISIBLE
                description.text = Html.fromHtml(singleHouseObject.description)
            }

//                    if (data.video.isNullOrEmpty()) {

            Glide.with(requireContext())
//                            .load("https://i.ytimg.com/vi/${videos?.get(position)}/maxresdefault.jpg")
                .load("https://i.ytimg.com/vi/-cYOlHknhBU/maxresdefault.jpg")
                .error(R.drawable.error_placeholder_midle)
                .placeholder(R.drawable.placeholder)
                .into(imageViewVideoPreloader)

            if(singleHouseObject.video.isNotEmpty()) {
                videoLayout.visibility = View.VISIBLE
                youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        val videoId =
                                "-cYOlHknhBU"//videos?.get(adapterPosition - items.size) ?: ""
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
            }else{
                videoLayout.visibility = View.GONE
            }
//                    }

            if (singleHouseObject.type == "house" || singleHouseObject.type == "flat") {
                housesListText.visibility = View.GONE
                listHouseBox.visibility = View.GONE
            }else{
                housesListText.visibility = View.VISIBLE
                listHouseBox.visibility = View.VISIBLE
            }

            if (singleHouseObject.type == "complex") {
                housesListText.text = "Список квартир"
            }


            if (singleHouseObject.advantages != null) {
                textViewAdvantages.visibility = View.VISIBLE
                whiteButtonAdvantagesRV.visibility = View.VISIBLE
                advantagesDivider.visibility = View.VISIBLE
                whiteButtonAdvantagesRV.adapter = AdvantagesAdapter(
                    requireContext(),
                    singleHouseObject.advantages
                )
            } else {
                textViewAdvantages.visibility = View.GONE
                whiteButtonAdvantagesRV.visibility = View.GONE
                advantagesDivider.visibility = View.GONE
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

            if (singleHouseObject.geolocation?.latitude == null
                    || singleHouseObject.geolocation.longitude == null){
                textViewLocationMap.visibility = View.GONE
                mapLayout.visibility = View.GONE
            }else{
                textViewLocationMap.visibility = View.VISIBLE
                mapLayout.visibility = View.VISIBLE
            }

            try {
                val latitude = singleHouseObject.geolocation?.latitude ?: .0
                val longitude = singleHouseObject.geolocation?.longitude ?: .0

                Log.e("test", "${latitude}:::${longitude}")

                mapview = mapView
                mapview?.map?.move(
                    CameraPosition(
                        Point(latitude, longitude), 11.0f, 0.0f, 0.0f
                    ),
                    Animation(Animation.Type.SMOOTH, 0F),
                    null
                )
                mapview?.map?.mapObjects?.addPlacemark(Point(latitude, longitude))
            } catch (e: Exception) {
                e.printStackTrace()
            }


            singleHouseObject.children

            if (singleHouseObject.children != null) {
                similarObjects.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = if(singleHouseObject.children.size > 1) {
                        ChildrenHouseAdapter(requireContext(), listOf(singleHouseObject.children[0], singleHouseObject.children[1])) { homeId ->
                            val home = App.setting.houses.firstOrNull { it.id == homeId }
                            val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                            findNavController().navigate(R.id.action_houseFragment_self, bundle)
                        }
                    }else{
                        ChildrenHouseAdapter(requireContext(), singleHouseObject.children) { homeId ->
                            val home = App.setting.houses.firstOrNull { it.id == homeId }
                            val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                            findNavController().navigate(R.id.action_houseFragment_self, bundle)
                        }
                    }
                    similarObjects.visibility = View.VISIBLE
                    textViewSimilarObject.visibility = View.VISIBLE
                }
            }else{
                similarObjects.visibility = View.GONE
                textViewSimilarObject.visibility = View.GONE
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
                        collapseListHouseBox.visibility = View.GONE
                        layoutManager = GridLayoutManager(context, 3)
                        if ((houseExampleData?.children?.size) ?: 0 <= 6) {
                            showListHouseBox.visibility = View.GONE
                            adapter = ListHouseBoxAdapter(
                                requireContext(),
                                houseExampleData?.children
                            )
                        } else {
                            houseExampleData?.apply {
                                showListHouseBox.visibility = View.VISIBLE
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
            if(list.size < 2) {
                dotsIndicator.visibility = View.INVISIBLE
            }else{
                dotsIndicator.visibility = View.VISIBLE
            }
            dotsIndicator.setViewPager2(vp)
        }
    }

    override fun onStop() {
        mapview?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview?.onStart()
    }

    override fun showInformation(position: Int) {
        houseObjectBinding?.whiteButtonRV?.scrollToPosition(position)
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