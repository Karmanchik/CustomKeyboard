package house.with.swimmingpool.ui.house

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.models.HouseExampleData
import house.with.swimmingpool.ui.*
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter
import house.with.swimmingpool.ui.house.adapters.*
import house.with.swimmingpool.ui.house.interfaces.ISingleHouseView
import house.with.swimmingpool.ui.popups.PopupActivity
import house.with.swimmingpool.ui.search.SearchActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HouseFragment : Fragment(), ISingleHouseView {

    private var houseObjectBinding: FragmentHouseBinding? = null
    private var houseExampleData: HouseExampleData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        houseObjectBinding = FragmentHouseBinding.inflate(layoutInflater)
        houseObjectBinding?.mapView2?.onCreate(savedInstanceState)
        return houseObjectBinding?.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            MapsInitializer.initialize(activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        houseObjectBinding?.houseBackIcon?.setOnClickListener {
            GlobalScope.async {
                isNeedOpenSearchActivity()
                launch(Dispatchers.Main) {
                    back()
                }
            }
        }

        houseObjectBinding?.apply {
            val size = shareLinkTextView.textSize
            noteLabel2.textSize = size
            favoriteTextView.textSize = size
        }

        try {
            val singleHouseObject =
                Gson().fromJson((arguments?.getString("home")), HouseExampleData::class.java)

            if (singleHouseObject != null) {
                showHome(singleHouseObject)

                RealtyServiceImpl().getHouseExample(
                    singleHouseObject.id ?: 0
                ) { data, e, _ ->
                    data?.let { showHome(it) }
                }

            } else {
                houseObjectBinding?.apply {
                    mainHeaderPlaceholder.visibility = View.VISIBLE
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
                    textViewPricePerMeter.visibility = View.INVISIBLE
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
        } catch (e: Exception) {
            Log.e("test", e.localizedMessage, e)
        }
    }

    private suspend fun isNeedOpenSearchActivity(){
        if (App.setting.isSearchActivityOpen) {
            startActivityForResult(Intent(requireContext(), SearchActivity::class.java), 0)
            App.setting.isSearchActivityOpen = false
        }
    }

    @SuppressLint("SetTextI18n")
    fun showHome(singleHouseObject: HouseExampleData) {
        try {
            houseObjectBinding?.apply {

                sendRequestButton.setOnClickListener {
                    val isPhoneNotEmpty = isPhoneConsultationFieldNotEmpty()
                    val isMessageNotEmpty = isMessageFieldNotEmpty()
                    if (isPhoneNotEmpty && isMessageNotEmpty) {
                        startActivity(
                            Intent(requireContext(), PopupActivity::class.java).apply {
                                putExtra(
                                    App.TYPE_OF_POPUP,
                                    App.SEND_REQUEST_CONSULTATION
                                )
                            }
                        )
                        sendRequest()
                    }
                }

                buttonCollMe.setOnClickListener {
                    if (isPhoneCollBackFieldNotEmpty()) {
                        startActivity(
                            Intent(requireContext(), PopupActivity::class.java).apply {
                                putExtra(
                                    App.TYPE_OF_POPUP,
                                    App.SEND_REQUEST_CONSULTATION
                                )
                            }
                        )
                        sendRequest()
                    }
                }

                shareLinkImageView.setOnClickListener { shareLink(singleHouseObject.id ?: 0) }

                shareLinkTextView.setOnClickListener { shareLink(singleHouseObject.id ?: 0) }

                singleHouseObject.apply {
                    if (isFavourite == true) {
                        favoriteImageView.setImageResource(R.drawable.ic_like_is_favorite_true)
                    } else {
                        favoriteImageView.setImageResource(R.drawable.ic_like_is_favorite_false)
                    }

                    favoriteImageView.setOnClickListener {
                        onClickButtonFavorite(this)
                    }
                    favoriteTextView.setOnClickListener {
                        onClickButtonFavorite(this)
                    }
                }

                if (App.setting.user?.phone != "") {
                    phoneInputConsultation.setText(App.setting.user?.phone)
                    phoneInputCollBack.setText(App.setting.user?.phone)
                }

                if (App.setting.user?.email != "") {
                    emailInput.setText(App.setting.user?.email)
                }

                houseExampleData = singleHouseObject

                singleHouseObject.getGallery().apply {
                    whiteButtonGalleryRV.adapter =
                        MainGalleryAndDateAdapter(
                            this?.filter { it.second.isNotEmpty() } ?: listOf(),
                            this@HouseFragment
                        )

                    val galleryNameList: ArrayList<String> = arrayListOf()
                    val listImage: ArrayList<String> = arrayListOf()

                    if (this.isNullOrEmpty() || this.size == 1) {
                        whiteButtonGalleryRV.visibility = View.GONE
                    } else {
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

                if (singleHouseObject.price_of_one_meter !== null) {
                    textViewPricePerMeter.text = singleHouseObject.price_of_one_meter + " руб/м²"
                } else {
                    textViewPricePerMeter.visibility = View.INVISIBLE
                }

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


                val descriptionText =
                    if (singleHouseObject.description != null) Html.fromHtml(singleHouseObject.description) else ""

                if (singleHouseObject.description?.trim()
                        .isNullOrEmpty() || descriptionText.isEmpty()
                ) {
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

                Glide.with(requireContext())
                    .load("https://i.ytimg.com/vi/-cYOlHknhBU/maxresdefault.jpg")
                    .error(R.drawable.error_placeholder_midl)
                    .placeholder(R.drawable.placeholder)
                    .dontAnimate()
                    .into(imageViewVideoPreloader)

                if (!singleHouseObject.video.isNullOrEmpty()) {
                    videoLayout.visibility = View.VISIBLE
                    youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            val videoId = singleHouseObject.video.first() ?: ""
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
                } else {
                    videoLayout.visibility = View.GONE
                }

                if (singleHouseObject.geolocation?.latitude == null) {
                    textViewLocationMap.visibility = View.GONE
                    mapLayout.visibility = View.GONE
                } else {
                    textViewLocationMap.visibility = View.VISIBLE
                    mapLayout.visibility = View.VISIBLE
                }

                if (singleHouseObject.type == "house" || singleHouseObject.type == "flat") {
                    housesListText.visibility = View.GONE
                    listHouseBox.visibility = View.GONE
                } else {
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

                try {
                    val icon = ImageView(requireContext()).apply {
                        setImageResource(R.drawable.ic_subtract)
                        layoutParams = ViewGroup.LayoutParams(15, 15)
                    }

                    val latitude = singleHouseObject.geolocation?.latitude ?: .0
                    val longitude = singleHouseObject.geolocation?.longitude ?: .0

                    mapView2.getMapAsync {
                        it.uiSettings.isRotateGesturesEnabled = false
                        it.uiSettings.isScrollGesturesEnabled = false
                        it.uiSettings.isZoomGesturesEnabled = false
                        it.uiSettings.isTiltGesturesEnabled = false

                        val marker = MarkerOptions().position(LatLng(latitude, longitude))
                        marker.icon(BitmapDescriptorFactory.fromBitmap(icon.drawable.toBitmap()))
                        it.addMarker(marker)
                        val cameraUpdate =
                            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 10f)
                        it.moveCamera(cameraUpdate)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }


                singleHouseObject.children

                if (singleHouseObject.analogs != null) {
                    similarObjects.apply {
                        layoutManager = GridLayoutManager(context, 2)
                        adapter =
                            ChildrenHouseAdapter(singleHouseObject.analogs.take(2)) { homeId ->
                                val home = singleHouseObject.analogs.firstOrNull { it.id == homeId }
                                val bundle =
                                    Bundle().apply { putString("home", Gson().toJson(home)) }
                                navigate(HouseFragment(), bundle)
                            }
                        similarObjects.visibility = View.VISIBLE
                        textViewSimilarObject.visibility = View.VISIBLE
                    }
                } else {
                    similarObjects.visibility = View.GONE
                    textViewSimilarObject.visibility = View.GONE
                }
                paperwork.setBannerClick(paperworkLayout, requireContext()) {}
            }
        } catch (e: Exception) {
        }
    }

    private fun sendRequest(isConsultation: Boolean = false) {
        houseObjectBinding?.apply {
            RealtyServiceImpl().consultationRequest(
                if (isConsultation) emailInput.text.toString() else null,
                phoneInputConsultation.text.toString(),
                if (isConsultation) editTextMessage.text.toString() else null
            ) { _, _ ->
            }
        }
    }

    private fun isMessageFieldNotEmpty(): Boolean {
        houseObjectBinding?.apply {
            return if (editTextMessage.text.isNullOrEmpty()) {
                messageBorder.setBackgroundResource(R.drawable.circle_corners6_error)
                false
            } else {
                messageBorder.setBackgroundResource(R.drawable.circle_corners6)
                true
            }
        }
        return false
    }

    private fun isPhoneConsultationFieldNotEmpty(): Boolean {
        houseObjectBinding?.apply {
            Log.e("test", phoneInputConsultation.rawText.length.toString())
            return if (phoneInputConsultation.rawText.length != 10) {
                phoneConsultationBorder.setBackgroundResource(R.drawable.circle_corners6_error)
                false
            } else {
                phoneConsultationBorder.setBackgroundResource(R.drawable.circle_corners6)
                true
            }
        }
        return false
    }

    private fun isPhoneCollBackFieldNotEmpty(): Boolean {
        houseObjectBinding?.apply {
            return if (phoneInputCollBack.rawText.length != 10) {
                phoneCollBackBorder.setBackgroundResource(R.drawable.circle_corners6_error)
                false
            } else {
                phoneCollBackBorder.setBackgroundResource(R.drawable.circle_corners6)
                true
            }
        }
        return false
    }

    private fun shareLink(id: Int) {
        toast("Copied profile link")
        val clipboard =
            requireActivity().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            "label",
            "https://domsbasseinom.ru/app/testhouse/$id"
        )
        clipboard.setPrimaryClip(clip)
    }

    private fun onClickButtonFavorite(singleHouseObject: HouseExampleData) {
        houseObjectBinding?.apply {
            singleHouseObject.apply {
                if (id != null) {
                    if (isFavourite == true) {
                        favoriteImageView.setImageResource(R.drawable.ic_like_is_favorite_false)
                        RealtyServiceImpl().removeFromFavourites(id) { _, _ ->
                            isFavourite = false
                        }
                    } else {
                        favoriteImageView.setImageResource(R.drawable.ic_like_is_favorite_true)
                        RealtyServiceImpl().addToFavourites(id) { _, _ ->
                            isFavourite = true
                        }
                    }
                }
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
                                adapter = ListHouseBoxAdapter(requireContext(), children?.take(6))
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
        houseObjectBinding?.mapView2?.onDestroy()
        houseObjectBinding = null
        super.onDestroy()
    }

    override fun onDestroyView() {
//        if (App.setting.isSearchActivityOpen) {
//            startActivityForResult(Intent(requireContext(), SearchActivity::class.java), 0)
//            App.setting.isSearchActivityOpen = false
//        }
        super.onDestroyView()
    }

    override fun showHeaderGallery(list: ArrayList<String>) {
        houseObjectBinding?.apply {
            mainHousesContainer.adapter = HouseHeaderAdapter(list)
            if (list.size < 2) {
                dotsIndicator.visibility = View.INVISIBLE
                mainHeaderPlaceholder.visibility = View.VISIBLE
            } else {
                dotsIndicator.visibility = View.VISIBLE
                mainHeaderPlaceholder.visibility = View.INVISIBLE
            }
            dotsIndicator.setViewPager2(mainHousesContainer)
        }
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

            childFragmentManager.beginTransaction()
                .replace(houseObjectBinding?.informationFrame?.id ?: 0, fragment)
                .commit()
        }
    }

    override fun onResume() {
        houseObjectBinding?.mapView2?.onResume()
        App.setting.filterConfig = null
        super.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        houseObjectBinding?.mapView2?.onLowMemory()
    }

}