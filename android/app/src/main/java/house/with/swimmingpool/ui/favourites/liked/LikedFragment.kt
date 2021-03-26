package house.with.swimmingpool.ui.favourites.liked

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFavouritesContainerLikedBinding
import house.with.swimmingpool.ui.favourites.searches.searchesBinding
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter

lateinit var binding: FragmentFavouritesContainerLikedBinding

class LikedFragment : Fragment(R.layout.fragment_favourites_container_liked){

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        binding = FragmentFavouritesContainerLikedBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{
            showCatalogButton.setOnClickListener {
                noObjectLayout.visibility = View.GONE
                likedRV.visibility = View.VISIBLE
                RealtyServiceImpl().getHouseCatalog { data, e ->
                    if (e == null && data != null) {
                        likedRV.adapter =
                            CatalogAdapter(data, requireContext()) {
                                val bundle = Bundle().apply {
                                    putInt("house", it)
                                }
                                findNavController().navigate(R.id.action_favouritesFragment_to_houseFragment, bundle)
                            }
                    } else {
                        Log.e("taf", e.toString())
                    }
                }
            }
        }
    }
}