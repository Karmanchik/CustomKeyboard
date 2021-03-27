package house.with.swimmingpool.ui.catalog

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
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.toast

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

    private fun showList(list: MutableList<HouseCatalogData>) {
        binding?.litRV?.adapter = CatalogAdapter(list.map { it as Any }.toMutableList().apply {
            add(4, "small")
            add(2, "big")
        }, requireContext()) { homeId ->
            val home = list.firstOrNull { it.id == homeId }
            val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
            findNavController().navigate(R.id.action_catalogViewModel_to_houseFragment, bundle)
        }
        binding?.conter?.text = "${list.size} предложений"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val list = App.setting.houses
            showList(list.toMutableList())
        } catch (e: Exception) {
            toast(e.localizedMessage)
            Log.e("test", "load catalog", e)
        }

        binding?.toFilterView?.setOnClickListener {
            findNavController().navigate(R.id.action_catalogViewModel_to_fullFilterFragment)
        }

        binding?.apply {
            closeFilterContainer.setOnClickListener { listFilterContainer.visibility = View.GONE }
            countFilters.setOnClickListener { listFilterContainer.visibility = View.VISIBLE }
            clearFilter.setOnClickListener {
                App.setting.filterConfig = null
                showFilter()
            }
            deleteFilter.setOnClickListener {
                App.setting.filterConfig = null
                showFilter()
            } // delete filter
            // показывать количество фильтров и вывод фильтра с листенерами
            // загружать если фильтр пустой
            // реклама

        }
        showFilter()
        if (App.setting.filterConfig == null) {
            RealtyServiceImpl().getHouseCatalog { data, e ->
                data?.let { list ->
                    showList(list.toMutableList())
                }
            }
        }
    }

    private fun showFilter() {
        binding?.filtersList?.adapter = FilterItemsAdapter(listOf("Ипотека", "Квартира")) {}
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}