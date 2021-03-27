package house.with.swimmingpool.ui.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
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
            }
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
        // delete filter
        // показывать количество фильтров и вывод фильтра с листенерами
        val labels = mutableListOf<Label>()
        //цена
        //площадь

//        // раен
//        districts = binding.area.value?.split(", ")
//            ?.mapNotNull { value -> districtsVariants?.entries?.firstOrNull { it.value == value }?.key },
//
//        // цена
//        price_all_from = (selectedPriceRange?.first ?: getPriceRange.first).toString(),
//        price_all_to = (selectedPriceRange?.second ?: getPriceRange.second).toString(),
//
//        // площадь
//        square_all_from = (selectedSquareRange?.first ?: getSquareRange.first).toString(),
//        square_all_to = (selectedSquareRange?.second ?: getSquareRange.second).toString(),
//
//        // оформление
//        registrationTypes = binding.docType.value?.split(", ")
//            ?.mapNotNull { value -> registrationTypeVariants?.entries?.firstOrNull { it.value == value }?.key },
//
//        // форма оплаты
//        paymentTypes = binding.moneyType.value?.split(", ")
//            ?.mapNotNull { value -> paymentTypeVariants?.entries?.firstOrNull { it.value == value }?.key },
//
//        // отделка
//        interiorTypes = binding.style.value?.split(", ")
//            ?.mapNotNull { value -> interiorVariants?.entries?.firstOrNull { it.value == value }?.key },
//
//        // класс дома
//        buildingClass = binding.houseType.value?.split(", ")
//            ?.mapNotNull { value -> buildingClassVariants?.entries?.firstOrNull { it.value == value }?.key },
//
//        // чипы
//        advantages = binding.chipGroup.children
//            .filter { it.tag == "2" }
//            .mapNotNull {
//                val text = (it as TextView).text.toString()
//                tagsVariants?.entries?.firstOrNull { it.value == text }?.key?.toString()
//            }.toList()

        binding?.filtersList?.adapter = FilterItemsAdapter(listOf())
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}