package house.with.swimmingpool.ui.favourites.searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentFavouritesContainerSearchesBinding


lateinit var searchesBinding: FragmentFavouritesContainerSearchesBinding

class SearchFragment : Fragment(R.layout.fragment_favourites_container_searches) {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        searchesBinding = FragmentFavouritesContainerSearchesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchesBinding.apply {
            searchMaterialRV
        }

    }

}