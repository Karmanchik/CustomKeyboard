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
import house.with.swimmingpool.databinding.FragmentCatalogBinding
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.toast

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

        try {
            val list = App.setting.houses

            binding?.litRV?.adapter = CatalogAdapter(list, requireContext()) { homeId ->
                val home = list.firstOrNull { it.id == homeId }
                val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                findNavController().navigate(R.id.action_catalogViewModel_to_houseFragment, bundle)
            }

            binding?.conter?.text = "${list.size} предложений"
        } catch (e: Exception) {
            toast(e.localizedMessage)
            Log.e("test", "load catalog", e)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}