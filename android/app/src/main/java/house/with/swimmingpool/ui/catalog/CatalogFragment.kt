package house.with.swimmingpool.ui.catalog

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCatalogBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.filter.full.FullFilterFragment
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.savefilter.SaveFilterFragment
import house.with.swimmingpool.ui.search.SearchActivity
import house.with.swimmingpool.ui.toast


class CatalogFragment : Fragment() {

    private var binding: FragmentCatalogBinding? = null

    private var lastDir = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCatalogBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    private fun showList(list: MutableList<HouseCatalogData>) {
        binding?.litRV?.adapter = CatalogAdapter(list.map { it as Any }.toMutableList().apply {
            try {
                add(4, "small")
                add(2, "big")
            } catch (e: Exception) {
            }
        }, requireContext()) { homeId ->
            binding?.sortMenu?.visibility = View.GONE
            val home = list.firstOrNull { it.id == homeId }
            val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
            navigate(HouseFragment(), bundle)
        }
        binding?.refresh?.isRefreshing = false
        binding?.conter?.text = "${list.size} ??????????????????????"
    }

    private fun showSortMenu() = binding?.apply {
        sortMenu.visibility = View.VISIBLE

        textViewPriceUp.setTextColor(Color.parseColor("#00A8FF"))
        textViewPriceDown.setTextColor(Color.parseColor("#00A8FF"))
        textViewPopular.setTextColor(Color.parseColor("#00A8FF"))
        textViewRating.setTextColor(Color.parseColor("#00A8FF"))
        imageView16.setImageResource(R.drawable.ic_up_blue_arrow_mono)
        priceDownIcon.setImageResource(R.drawable.ic_up_blue_arrow_mono)

        when (lastDir) {
            "asc" -> {
                textViewPriceUp.setTextColor(Color.parseColor("#A0AABA"))
                imageView16.setImageResource(R.drawable.ic_arrow_up_blocked)
            }
            "desc" -> {
                textViewPriceDown.setTextColor(Color.parseColor("#A0AABA"))
                priceDownIcon.setImageResource(R.drawable.ic_arrow_up_blocked)
            }
            "hits" -> {
                textViewPopular.setTextColor(Color.parseColor("#A0AABA"))
            }
            "rank" -> {
                textViewRating.setTextColor(Color.parseColor("#A0AABA"))
            }
        }

        priceUp.setOnClickListener {
            if (lastDir == "asc") return@setOnClickListener
            sortByPriceDir("asc")
            textViewPriceUp.setTextColor(Color.parseColor("#A0AABA"))
            textViewPriceDown.setTextColor(Color.parseColor("#00A8FF"))
        }

        priceDown.setOnClickListener {
            if (lastDir == "desc") return@setOnClickListener
            sortByPriceDir("desc")
            textViewPriceUp.setTextColor(Color.parseColor("#00A8FF"))
            textViewPriceDown.setTextColor(Color.parseColor("#A0AABA"))
        }

        popular.setOnClickListener {
            if (lastDir == "hits") return@setOnClickListener
            sortByPriceDir("hits")
        }

        rating.setOnClickListener {
            if (lastDir == "rank") return@setOnClickListener
            sortByPriceDir("rank")
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            refresh.isRefreshing = true
            refresh.setOnRefreshListener { showFilter() }

            back.setOnClickListener { back() }

            sort.setOnClickListener {
                showSortMenu()
            }

            closeSort.setOnClickListener {
                sortMenu.visibility = View.GONE
            }
            toolbar.setOnClickListener {
                sortMenu.visibility = View.GONE
            }
        }

        binding?.litRV?.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (oldScrollY > scrollY) {
                binding?.toFilter?.visibility = View.VISIBLE
            } else {
                binding?.toFilter?.visibility = View.GONE
            }
            binding?.sortMenu?.visibility = View.GONE
        }

        binding?.toFilter?.setOnClickListener {
            navigate(FullFilterFragment())
        }
        showFilter()

        binding?.addFilter?.setOnClickListener {
            if(App.setting.isAuth) {
                if (App.setting.filterConfig == null) {
                    toast("???????????????????? ???????????? ?????? ????????????????????!")
                } else {
                    SaveFilterFragment.newInstance()
                            .show(parentFragmentManager, SaveFilterFragment::class.java.simpleName)
                }
            }else{
                navigate(CabinetFragment())
            }
        }
    }

    private fun sortByPriceDir(dir: String) {
        if(dir != lastDir) {
            binding?.apply {

                textViewPriceUp.setTextColor(Color.parseColor("#00A8FF"))
                textViewPriceDown.setTextColor(Color.parseColor("#00A8FF"))
                textViewPopular.setTextColor(Color.parseColor("#00A8FF"))
                textViewRating.setTextColor(Color.parseColor("#00A8FF"))

                val request = App.setting.filterConfig ?: FilterObjectsRequest()
                lastDir = dir
                request.sort = "price"
                request.dir = dir
                App.setting.filterConfig = request
                showFilter()
                binding?.sortMenu?.visibility = View.GONE

                when(dir){
                    "asc" ->{textViewPriceUp.setTextColor(Color.parseColor("#A0AABA"))}
                    "desc" ->{textViewPriceDown.setTextColor(Color.parseColor("#A0AABA"))}
                    "hits" ->{textViewPopular.setTextColor(Color.parseColor("#A0AABA"))}
                    "rank" ->{textViewRating.setTextColor(Color.parseColor("#A0AABA"))}
                }
            }
        }
    }

    private fun showFilter() {
        binding?.refresh?.isRefreshing = true
        RealtyServiceImpl().getObjectsByFilter(
                App.setting.filterConfig ?: FilterObjectsRequest()
        ) { data, e ->
            val list = data ?: listOf()
            showList(list.toMutableList())
            binding?.apply{
                conter.visibility = View.VISIBLE
                bigBanner.visibility = View.VISIBLE
            }
        }
    }



    override fun onDestroy() {
        binding = null
        if (App.setting.isSearchActivityOpen) {
            startActivityForResult(Intent(requireContext(), SearchActivity::class.java), 0)
            App.setting.isSearchActivityOpen = false
        }
        super.onDestroy()
    }

}