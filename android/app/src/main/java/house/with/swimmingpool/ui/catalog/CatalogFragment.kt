package house.with.swimmingpool.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentCatalogBinding
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.litRV?.adapter = CatalogAdapter(App.setting.houses, requireContext()) { homeId ->
            val home = App.setting.houses.firstOrNull { it.id == homeId }
            val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
            findNavController().navigate(R.id.action_catalogViewModel_to_houseFragment, bundle)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}