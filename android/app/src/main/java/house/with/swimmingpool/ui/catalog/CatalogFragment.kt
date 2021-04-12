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
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCatalogBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.back
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
        }.take(50), requireContext()) { homeId ->
            val home = list.firstOrNull { it.id == homeId }
            val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
            navigate(HouseFragment(), bundle)
        }
        binding?.refresh?.isRefreshing = false
        binding?.conter?.text = "${list.size} предложений"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            refresh.isRefreshing = true
            refresh.setOnRefreshListener { binding?.refresh?.isRefreshing = false }

            back.setOnClickListener { back() }

            sort.setOnClickListener {
                sortMenu.visibility =
                        if (sortMenu.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            closeSort.setOnClickListener {
                sortMenu.visibility = View.GONE
            }

            priceUp.setOnClickListener {
                sortByPriseDir("asc")
                textViewPriceUp.setTextColor(Color.parseColor("#A0AABA"))
                textViewPriceDown.setTextColor(Color.parseColor("#00A8FF"))
            }

            priceDown.setOnClickListener {
                sortByPriseDir("desc")
                textViewPriceUp.setTextColor(Color.parseColor("#00A8FF"))
                textViewPriceDown.setTextColor(Color.parseColor("#A0AABA"))
            }
        }

//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val list = App.setting.houses
//                launch(Dispatchers.Main) {
//                    showList(list.toMutableList())
//                }
//            } catch (e: Exception) {
//                Log.e("test", "load catalog", e)
//            }
//        }

        binding?.scroll?.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (oldScrollY > scrollY) {
                binding?.toFilter?.visibility = View.VISIBLE
            } else {
                binding?.toFilter?.visibility = View.GONE
            }
        }

        binding?.toFilter?.setOnClickListener {
            navigate(FullFilterFragment())
        }
        showFilter()

        binding?.addFilter?.setOnClickListener {
            if (App.setting.filterConfig == null) {
                toast("Установите фильтр для сохранения!")
            } else {
                SaveFilterFragment.newInstance()
                        .show(parentFragmentManager, SaveFilterFragment::class.java.simpleName)
            }
        }
    }

    private fun sortByPriseDir(dir: String) {
        if(dir != lastDir) {
            val request = App.setting.filterConfig ?: FilterObjectsRequest()
            lastDir = dir
            request.sort = "price"
            request.dir = dir
            App.setting.filterConfig = request
            showFilter()
            binding?.sortMenu?.visibility = View.GONE
        }
    }

    private fun showFilter() {
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