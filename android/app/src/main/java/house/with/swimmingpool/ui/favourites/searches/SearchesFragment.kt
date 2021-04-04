package house.with.swimmingpool.ui.favourites.searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFavouritesContainerSearchesBinding
import house.with.swimmingpool.models.HouseOptions
import house.with.swimmingpool.models.Search
import house.with.swimmingpool.ui.favourites.adapters.FavoritesContainerSearchesAdapter

class SearchesFragment : Fragment(R.layout.fragment_favourites_container_searches) {

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

        RealtyServiceImpl().getSearches { data, _ ->
            showData(data ?: listOf())
        }
    }

    private fun showData(list: List<Search>) {
        searchesBinding?.apply {
            noObjectLayout.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            objectsLayout.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

            searchedElementRV.adapter = FavoritesContainerSearchesAdapter(list) {}
        }
    }

    override fun onDestroy() {
        searchesBinding = null
        super.onDestroy()
    }

}