package house.with.swimmingpool.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.*
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.filter.full.FullFilterFragment
import house.with.swimmingpool.ui.filter.short.ShortFilterFragment
import house.with.swimmingpool.ui.home.adapters.*
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.load
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.news.listnews.NewsListFragment
import house.with.swimmingpool.ui.news.singlenews.NewsSingleFragment
import house.with.swimmingpool.ui.popups.PopupActivity
import house.with.swimmingpool.ui.search.SearchActivity
import house.with.swimmingpool.ui.showModeBottomMenu
import house.with.swimmingpool.ui.showModeFab
import house.with.swimmingpool.ui.videos.listvideos.VideosListFragment
import house.with.swimmingpool.ui.videos.singlevideo.VideoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    companion object {
        var isFirstLoad = true
        const val NAVIGATE_TO_CATALOG = 1
        const val NAVIGATE_TO_OBJECT = 2
        const val POPUP_WIFI_ERROR_REFRASH = 201
    }

    private var homeBinding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return homeBinding?.root
    }

    override fun onDestroy() {
        homeBinding = null
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (resultCode == RESULT_OK) {
//            data?.getIntExtra("action", 0)?.let { code ->
//                if (code == NAVIGATE_TO_CATALOG) {
//                    navigate(CatalogFragment())
//                } else if (code == NAVIGATE_TO_OBJECT) {
//                    val bundle =
//                        Bundle().apply { putString("home", Gson().toJson(App.setting.tmpObj)) }
//                    navigate(HouseFragment(), bundle)
//                }
//            }
//        }

        if (requestCode == POPUP_WIFI_ERROR_REFRASH) {
            updateData()
        }
    }

    private fun updateData() {

        if (isFirstLoad) {
            homeBinding?.firstLoad?.visibility = View.VISIBLE
            showModeFab(false)
            showModeBottomMenu(false)
            isFirstLoad = false
        } else {
            homeBinding?.loader?.visibility = View.VISIBLE
        }

        AuthServiceImpl().getSettings { data, e ->
            if (data != null && e == null) {
                App.setting.registerImageLink = data
                    .getAsJsonArray("SiteSettings")
                    .firstOrNull { it.asJsonObject["key"].asString == "app_mobile_welcome_pic" }
                    ?.asJsonObject?.get("value")?.asString.toString()
            } else {
                App.setting.registerImageLink = null
            }
        }

        AuthServiceImpl().getSettingsPhone { data, e ->
            App.setting.settingPhone = data
        }

        GlobalScope.launch(Dispatchers.IO) {
            val videosInfo = VideosServiceImpl().loadVideos()
            val newsInfo = NewsServiceImpl().loadNews()
            val storiesInfo = StoriesServiceImpl().loadStories()
            val headerInfo = BannersServiceImpl().loadHeader()
            val ads = BannersServiceImpl().loadBanners("home")

            launch(Dispatchers.Main) {
                showModeFab(true)
                showModeBottomMenu(true)

                if (videosInfo.second != null
                    && newsInfo.second != null
                    && storiesInfo.second != null
                    && headerInfo.second != null
                    && ads.second != null
                ) {
                    startActivityForResult(
                        Intent(requireContext(), PopupActivity::class.java).apply {
                            putExtra(App.TYPE_OF_POPUP, App.INTERNET_ERROR)
                        },
                        POPUP_WIFI_ERROR_REFRASH
                    )
                    homeBinding?.nestedScrollView?.visibility = View.GONE
                } else {
                    homeBinding?.nestedScrollView?.visibility = View.VISIBLE
                }


                homeBinding?.loader?.visibility = View.GONE
                homeBinding?.firstLoad?.visibility = View.GONE

                homeBinding?.storiesRV?.apply {
                    visibility = if (storiesInfo.first.isNullOrEmpty()) View.GONE else View.VISIBLE
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = StoriesAdapter(storiesInfo.first ?: listOf())
                }

                homeBinding?.nestedScrollView?.visibility =
                    if (headerInfo.first.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }

                homeBinding?.apply {
                    val vp = mainHousesContainer
                    vp.adapter = HeaderAdapter(headerInfo.first ?: listOf())
                    { link ->

                        val splitLink = link.split("/")

                        when (splitLink.first().toString()) {
                            "objects" -> {
                                val home = App.setting.houses.firstOrNull {
                                    it.id == splitLink.last().toInt()
                                }

                                if (home == null) {
                                    RealtyServiceImpl().getHouseExample(
                                        splitLink.last().toInt()
                                    ) { data, e, _ ->
                                        val bundle =
                                            Bundle().apply {
                                                putString(
                                                    "home",
                                                    Gson().toJson(data)
                                                )
                                            }
                                        navigate(HouseFragment(), bundle)
                                    }
                                } else {
                                    val bundle =
                                        Bundle().apply { putString("home", Gson().toJson(home)) }
                                    navigate(HouseFragment(), bundle)
                                }
                            }
                            "news" -> {
                                val bundle = Bundle().apply {
                                    putInt("id", splitLink.last().toInt())
                                }
                                navigate(NewsSingleFragment(), bundle)
                            }
                            "video" -> {
                                val bundle = Bundle().apply {
                                    putInt("id", splitLink.last().toInt())
                                }
                                navigate(VideoFragment(), bundle)
                            }
                            "https:" -> {
                                val browserIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        link
                                    )
                                )
                                startActivity(browserIntent);
                            }
                        }

                        Log.e("mainBannersTestLink", splitLink.toString())
                    }
                    dotsIndicator.setViewPager2(vp)

                }

                if (!ads.first.isNullOrEmpty()) {
                    homeBinding?.apply {
                        bigAdBanner.load(ads.first?.get(0)?.bigBanner)
                        firstAdBanner.load(ads.first?.get(1)?.smallBanner)
                        secondAdBanner.load(ads.first?.get(2)?.smallBanner)

                        bigBannerLayout.visibility = View.VISIBLE
                        adsLinear.visibility = View.VISIBLE
                    }
                } else {
                    homeBinding?.bigBannerLayout?.visibility = View.VISIBLE
                    homeBinding?.adsLinear?.visibility = View.VISIBLE
                }

                if (videosInfo.second == null && videosInfo.first != null) {
                    homeBinding?.videosRV?.adapter =
                        VideosAdapter(false, requireContext(), videosInfo.first ?: listOf()) {
                            val bundle = Bundle().apply {
                                putInt("id", it)
                            }
                            navigate(
                                VideoFragment(),
                                bundle,
                                inAnim = R.anim.slide_in_left,
                                outAnim = R.anim.slide_out_right
                            )
                        }
                    homeBinding?.videosContainer?.visibility = View.VISIBLE
                } else {
                    homeBinding?.videosContainer?.visibility = View.GONE
                }

                if (newsInfo.second == null && newsInfo.first != null) {
                    homeBinding?.newsRV?.adapter = NewsAdapter(
                        newsInfo.first?.take(2) ?: listOf(),
                        requireContext()
                    ) {
                        val bundle = Bundle().apply {
                            putInt("id", it.id ?: 0)
                            putSerializable("house", it)
                        }
                        navigate(NewsSingleFragment(), bundle)
                    }
                    homeBinding?.newsContainer?.visibility = View.VISIBLE
                } else {
                    homeBinding?.newsContainer?.visibility = View.GONE
                }
            }
        }

//            RealtyServiceImpl().getHouseCatalog { data, e ->
//                lastSeenRV.apply {
//                    layoutManager = GridLayoutManager(context, 2)
//                    adapter = SeenHousesAdapter(requireContext(), data ?: listOf()) { homeId ->
//                        val home = data?.firstOrNull { it.id == homeId }
//                        val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
//                        indNavController().navigate(
//                            R.id.action_navigation_home_to_houseFragment,
//                            bundle
//                        )
//                    }
//                }
//            }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var houseCatalogData: List<HouseCatalogData>? = null

        homeBinding?.apply {

            imageViewSearch.setOnClickListener {
                startActivityForResult(Intent(requireContext(), SearchActivity::class.java), 0)
            }

            updateData()

            videosRV


            nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if ((shortCatalogRV.bottom in (oldScrollY + 1) until scrollY)
                    || (textViewShortCatalog.top - coordinateLayout.height in (scrollY + 1) until oldScrollY)
                ) {
                    (shortCatalogRV.adapter as? CatalogAdapter)?.stopVideo?.invoke()
                }
            }

            segmentedControl.addOnSegmentClickListener { svh ->
                RealtyServiceImpl().getHouseCatalog { data, e ->
                    if (e == null && data != null) {
                        houseCatalogData = when (svh.absolutePosition) {
                            0 -> {
                                data.filter { it.type == "house" || it.type == "village" }
                            }
                            1 -> {
                                data.filter { it.type == "flat" }
                            }
                            else -> {
                                data.filter { it.type == "complex" }
                            }
                        }
                        setShortCatalog(houseCatalogData)
                    }
                }
            }

            segmentedControl.setTypeFace(
                ResourcesCompat.getFont(requireContext().applicationContext, R.font.lato_regular)
            )

            segmentedControl.setSelectedSegment(0)

            shortFilterView.setOnClickListener {
                val onClick = { navigate(CatalogFragment()) }
                ShortFilterFragment(onClick).newInstance(onClick).show(
                    parentFragmentManager, "shortFilter"
                )
            }

            fullFilterView.setOnClickListener {
                navigate(FullFilterFragment())
            }

            showCatalogButton.setOnClickListener {
                App.setting.filterConfig = null
                navigate(CatalogFragment())
            }

            showVideosButton.setOnClickListener {
                navigate(VideosListFragment())
            }

            showNewsButton.setOnClickListener {
                navigate(NewsListFragment())
            }

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    moveOnTabSelected(tab!!.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    moveOnTabSelected(tab!!.position)
                }
            })

//            bigAdBanner.setBannerClick(bigBannerLayout, requireContext()) {}
//            firstAdBanner.setBannerClick(firstAdBannerLayout, requireContext()) {}
//            secondAdBanner.setBannerClick(secondAdBannerLayout, requireContext()) {}
//            bigAdCoinsBanner.setBannerClick(bigAdCoinsBannerLayout, requireContext()) {}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setShortCatalog(data: List<HouseCatalogData>?) {
        homeBinding?.apply {
            if (data != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    App.setting.houses = data
                }

                textViewVideosCount.text = "${data.size} предложений"
                shortCatalogRV.adapter =
                    CatalogAdapter(data.take(2), requireContext()) { homeId ->
                        val home = data.firstOrNull { it.id == homeId }
                        val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                        navigate(HouseFragment(), bundle)
                    }
            }
        }
    }

    private fun moveOnTabSelected(position: Int) {
        homeBinding?.apply {
            val pos = when (position) {
                0 -> fullFilterView.bottom
                1 -> adsLinear.bottom
                2 -> videosContainer.top
                3 -> newsContainer.top
                else -> null
            }
            pos?.let { nestedScrollView.smoothScrollTo(0, it, 1500) }
        }
    }
}