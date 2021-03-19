package house.with.swimmingpool.ui.house

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.models.HouseCatalogData

class HouseFragment : Fragment(R.layout.fragment_house) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if ()
        val house = arguments?.getSerializable("house") as? HouseCatalogData
    }

}