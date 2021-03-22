package house.with.swimmingpool.ui.favourites.houses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentFavouritesContainerHousesBinding
import house.with.swimmingpool.ui.favourites.adapters.SelectionHouseAdapter



class HousesFragment : Fragment(R.layout.fragment_favourites_container_houses){

    private var housesBinding: FragmentFavouritesContainerHousesBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        housesBinding = FragmentFavouritesContainerHousesBinding.inflate(layoutInflater)

        return housesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        housesBinding?.apply {
            housesRV.adapter = SelectionHouseAdapter(requireContext(), listOf("", "", ""))
        }

    }

    override fun onDestroy() {
        housesBinding = null
        super.onDestroy()
    }

}