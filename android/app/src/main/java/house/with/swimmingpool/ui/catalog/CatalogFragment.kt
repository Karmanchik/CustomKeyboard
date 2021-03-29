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
    }

    private fun showFilter() {
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