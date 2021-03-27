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
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCatalogBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.toast

class CatalogFragment : Fragment() {

    private val filterConfig get() = App.setting.filterVariants
    private val filterCategories get() = filterConfig?.entrySet()?.map { Pair(it.key, it.value) }

    private val getPriceRange
        get() = Pair(
            filterCategories?.firstOrNull { it.first == "minPrice" }?.second?.asInt ?: 0,
            filterCategories?.firstOrNull { it.first == "maxPrice" }?.second?.asInt ?: 0
        )

    private val getSquareRange
        get() = Pair(
            filterCategories?.firstOrNull { it.first == "minSquare" }?.second?.asInt ?: 0,
            filterCategories?.firstOrNull { it.first == "maxSquare" }?.second?.asInt ?: 0
        )

    private val districtsVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "districts" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val registrationTypeVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "registration_types" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val paymentTypeVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "payment_types" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val interiorVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "interior" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val buildingClassVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "building_class" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val tagsVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "advantages" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

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
            try {
                add(4, "small")
                add(2, "big")
            } catch (e: Exception) {
            }
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
        binding?.countFilters?.text = "Выбрано (0)"
        val filter = App.setting.filterConfig
        if (filter == null) {
            binding?.listFilterContainer?.visibility = View.GONE
            binding?.filtersList?.adapter = null
            return
        }

        val labels = mutableListOf<Label>()

        //цена
        if (filter.price_all_from != null) labels.add(Label("От ${filter.price_all_from}р.") {
            App.setting.filterConfig = App.setting.filterConfig?.apply { price_all_from = null }
            showFilter()
        })
        if (filter.price_all_to != null) labels.add(Label("До ${filter.price_all_to}р.") {
            App.setting.filterConfig = App.setting.filterConfig?.apply { price_all_to = null }
            showFilter()
        })

        //площадь
        if (filter.square_all_from != null) labels.add(Label("От ${filter.square_all_from}м2") {
            App.setting.filterConfig = App.setting.filterConfig?.apply { square_all_from = null }
            showFilter()
        })
        if (filter.square_all_to != null) labels.add(Label("До ${filter.square_all_to}м2") {
            App.setting.filterConfig = App.setting.filterConfig?.apply { square_all_to = null }
            showFilter()
        })

        // раен
        filter.districts?.filter { districtsVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(districtsVariants!![item]!!) {
                    App.setting.filterConfig = App.setting.filterConfig?.apply {
                        districts = districts?.filterNot { it == item }
                    }
                    showFilter()
                }
            }
            ?.forEach { labels.add(it) }

        // оформление
        filter.registrationTypes?.filter { registrationTypeVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(registrationTypeVariants!![item]!!) {
                    App.setting.filterConfig = App.setting.filterConfig?.apply {
                        registrationTypes = registrationTypes?.filterNot { it == item }
                    }
                    showFilter()
                }
            }
            ?.forEach { labels.add(it) }

        // форма оплаты
        filter.paymentTypes?.filter { paymentTypeVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(paymentTypeVariants!![item]!!) {
                    App.setting.filterConfig = App.setting.filterConfig?.apply {
                        paymentTypes = paymentTypes?.filterNot { it == item }
                    }
                    showFilter()
                }
            }
            ?.forEach { labels.add(it) }

        // отделка
        filter.interiorTypes?.filter { interiorVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(interiorVariants!![item]!!) {
                    App.setting.filterConfig = App.setting.filterConfig?.apply {
                        interiorTypes = interiorTypes?.filterNot { it == item }
                    }
                    showFilter()
                }
            }
            ?.forEach { labels.add(it) }

        // класс дома
        filter.buildingClass?.filter { buildingClassVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(buildingClassVariants!![item]!!) {
                    App.setting.filterConfig = App.setting.filterConfig?.apply {
                        buildingClass = buildingClass?.filterNot { it == item }
                    }
                    showFilter()
                }
            }
            ?.forEach { labels.add(it) }

        // чипы
        filter.advantages?.filter { tagsVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(tagsVariants!![item]!!) {
                    App.setting.filterConfig = App.setting.filterConfig?.apply {
                        advantages = advantages?.filterNot { it == item }
                    }
                    showFilter()
                }
            }
            ?.forEach { labels.add(it) }

        binding?.filtersList?.adapter = FilterItemsAdapter(labels)
        binding?.countFilters?.text = "Выбрано (${labels.size})"

        RealtyServiceImpl().getObjectsByFilter(App.setting.filterConfig ?: FilterObjectsRequest()) { data, e ->
            val list = data ?: listOf()
            showList(list.toMutableList())
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}