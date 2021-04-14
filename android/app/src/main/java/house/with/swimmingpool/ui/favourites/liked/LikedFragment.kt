package house.with.swimmingpool.ui.favourites.liked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFavouritesContainerLikedBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.navigate

class LikedFragment : Fragment() {

    private var binding: FragmentFavouritesContainerLikedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentFavouritesContainerLikedBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.showCatalogButton?.setOnClickListener {
            navigate(CatalogFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        RealtyServiceImpl().getMyFavourites { data, e ->
            showData(data?.list ?: listOf())
        }
    }

    private fun showData(list: List<HouseCatalogData>) {
        binding?.apply {
            noObjectLayout.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            likedRV.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

            likedRV.adapter =
                CatalogAdapter(list.map { it as Any }, requireContext()) { homeId ->
                    val home = list.firstOrNull { it.id == homeId }
                    val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                    navigate(HouseFragment(), bundle)
                }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}