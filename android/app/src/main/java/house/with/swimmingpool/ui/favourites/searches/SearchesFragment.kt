package house.with.swimmingpool.ui.favourites.searches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFavouritesContainerSearchesBinding
import house.with.swimmingpool.models.Search
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.catalog.Label
import house.with.swimmingpool.ui.favourites.adapters.FavoritesContainerSearchesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchesFragment : Fragment() {

    private var filterConfig: JsonObject? = null
    private val filterCategories get() = filterConfig?.entrySet()?.map { Pair(it.key, it.value) }

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

    private var searchesBinding: FragmentFavouritesContainerSearchesBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        searchesBinding = FragmentFavouritesContainerSearchesBinding.inflate(layoutInflater)
        return searchesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchesBinding?.showCatalogButton?.setOnClickListener {
            findNavController().navigate(R.id.action_favouritesFragment_to_catalogViewModel)
        }

        update()
    }

    private fun update() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                RealtyServiceImpl().getParamsForFilter()?.data?.let {
                    launch(Dispatchers.Main) {
                        filterConfig = it
                        RealtyServiceImpl().getSearches { data, _ ->
                            showData(data ?: listOf())
                        }
                    }
                }
            } catch (e: Exception) {
                showData(listOf())
                Log.e("test", "load", e)
            }
        }
    }

    private fun showFilter(filter: FilterObjectsRequest): List<Label> {
        val labels = mutableListOf<Label>()

        // раен
        filter.districts?.filter { districtsVariants?.containsKey(it) == true }
            ?.map { item -> Label(districtsVariants!![item]!!) {} }
            ?.forEach { labels.add(it) }

        // оформление
        filter.registrationTypes?.filter { registrationTypeVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(registrationTypeVariants!![item]!!) {}
            }
            ?.forEach { labels.add(it) }

        // форма оплаты
        filter.paymentTypes?.filter { paymentTypeVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(paymentTypeVariants!![item]!!) {}
            }
            ?.forEach { labels.add(it) }

        // отделка
        filter.interiorTypes?.filter { interiorVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(interiorVariants!![item]!!) {}
            }
            ?.forEach { labels.add(it) }

        // класс дома
        filter.buildingClass?.filter { buildingClassVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(buildingClassVariants!![item]!!) {}
            }
            ?.forEach { labels.add(it) }

        // чипы
        filter.advantages?.filter { tagsVariants?.containsKey(it) == true }
            ?.map { item ->
                Label(tagsVariants!![item]!!) {}
            }
            ?.forEach { labels.add(it) }

        return labels
    }


    private fun showData(list: List<Search>) {
        searchesBinding?.apply {
            noObjectLayout.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            objectsLayout.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

            searchedElementRV.adapter = FavoritesContainerSearchesAdapter(
                items = list,
                getLabels = ::showFilter,
                delete = {
                    RealtyServiceImpl().deleteSearch(it.id.toString()) { _, _ ->
                        update()
                    }
                },
                openSearch = {
                    App.setting.filterConfig = it.config
                    findNavController().navigate(R.id.action_favouritesFragment_to_catalogViewModel)
                },
                push = {
                    it.config?:return@FavoritesContainerSearchesAdapter
                    RealtyServiceImpl().updateSearch(it.id.toString(), it.name ?: "", it.push?.not() ?: false, it.config) { _, _ ->
                        update()
                    }
                }
            )
        }
    }

    override fun onDestroy() {
        searchesBinding = null
        super.onDestroy()
    }

}