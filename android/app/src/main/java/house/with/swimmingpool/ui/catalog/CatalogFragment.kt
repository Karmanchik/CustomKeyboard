package house.with.swimmingpool.ui.catalog

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCatalogBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.savefilter.SaveFilterFragment
import house.with.swimmingpool.ui.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CatalogFragment : Fragment() {

    private var binding: FragmentCatalogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
            findNavController().navigate(R.id.action_catalogViewModel_to_houseFragment, bundle)
        }
        binding?.refresh?.isRefreshing = false
        binding?.conter?.text = "${list.size} предложений"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.refresh?.isRefreshing = true
        binding?.refresh?.setOnRefreshListener { binding?.refresh?.isRefreshing = false }

        binding?.back?.setOnClickListener { findNavController().popBackStack() }

        binding?.apply {
            sort.setOnClickListener {
                sortMenu.visibility = if (sortMenu.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            closeSort.setOnClickListener { sortMenu.visibility = View.GONE }


            priceUp.setOnClickListener {
                val request = App.setting.filterConfig ?: FilterObjectsRequest()
                request.sort = "price"
                request.dir = "asc"
                App.setting.filterConfig = request
                showFilter()
                closeSort.performClick()
            }
            priceDown.setOnClickListener {
                val request = App.setting.filterConfig ?: FilterObjectsRequest()
                request.sort = "price"
                request.dir = "desc"
                App.setting.filterConfig = request
                showFilter()
                closeSort.performClick()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val list = App.setting.houses
                launch(Dispatchers.Main) {
                    showList(list.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("test", "load catalog", e)
            }
        }

        binding?.scroll?.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (oldScrollY > scrollY) {
                binding?.toFilter?.visibility = View.VISIBLE
            } else {
                binding?.toFilter?.visibility = View.GONE
            }
        }

        binding?.toFilter?.setOnClickListener {
            findNavController().navigate(R.id.action_catalogViewModel_to_fullFilterFragment)
        }
        showFilter()
        if (App.setting.filterConfig == null) {
            RealtyServiceImpl().getHouseCatalog { data, e ->
                data?.let { list ->
                    showList(list.toMutableList())
                }
            }
        }

        binding?.addFilter?.setOnClickListener {
            if (App.setting.filterConfig == null) {
                toast("Установите фильтр для сохранения!")
            } else {
                SaveFilterFragment.newInstance().show(parentFragmentManager, SaveFilterFragment::class.java.simpleName)
            }
        }
    }

    private fun showFilter() {
        RealtyServiceImpl().getObjectsByFilter(
            App.setting.filterConfig ?: FilterObjectsRequest()
        ) { data, e ->
            val list = data ?: listOf()
            showList(list.toMutableList())
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}