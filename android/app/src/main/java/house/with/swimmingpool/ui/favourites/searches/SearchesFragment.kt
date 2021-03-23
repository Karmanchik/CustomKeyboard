package house.with.swimmingpool.ui.favourites.searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentFavouritesContainerSearchesBinding
import house.with.swimmingpool.models.HouseOptions
import house.with.swimmingpool.ui.favourites.adapters.FavoritesContainerSearchesAdapter


lateinit var searchesBinding: FragmentFavouritesContainerSearchesBinding

class SearchesFragment : Fragment(R.layout.fragment_favourites_container_searches) {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        searchesBinding = FragmentFavouritesContainerSearchesBinding.inflate(layoutInflater)

        return searchesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchesBinding.apply {
            showCatalogButton.setOnClickListener {

                noObjectLayout.visibility = View.GONE
                objectsLayout.visibility = View.VISIBLE

                searchedElementRV.adapter = FavoritesContainerSearchesAdapter(requireContext(), listOf(
                    HouseOptions("Купить", listOf("Ипотека","Рассрочка")),
                    HouseOptions("Купить", listOf("Ипотека","Рассрочка","Сад","Спа салон","Фитнесс зал"))
                ))
            }
        }

    }

}