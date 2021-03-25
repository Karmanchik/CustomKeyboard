package house.with.swimmingpool.ui.house

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.MainTags
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter
import house.with.swimmingpool.ui.home.adapters.SeenHousesAdapter
import house.with.swimmingpool.ui.house.adapters.*
import java.io.File
import java.io.FileInputStream

class HouseFragment : Fragment() {

    private var houseObjectBinding: FragmentHouseBinding? = null

    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        houseObjectBinding = FragmentHouseBinding.inflate(layoutInflater)

        return houseObjectBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val house = arguments?.getSerializable("house") as? HouseCatalogData

        houseObjectBinding?.apply {
            val vp = mainHousesContainer
            vp.adapter = HouseHeaderAdapter(listOf("", "", ""))

            dotsIndicator.setViewPager2(vp)

            hashTagRV.adapter = TagAdapter(requireContext(), listOf(
                MainTags("#4890FB", "Ипотека"),
                MainTags("#4890FB", "Ипотека"),
                MainTags("#4890FB", "Ипотека"))
            )

            whiteButtonFilterRV.adapter = MainGalleryAndDateAdapter(requireContext(), listOf("Главная галерея", "Декабрь 2020", "Февраль 2020"))

            whiteButtonRV.adapter = WhiteButtonAdapter(requireContext(), listOf("Общие", "Коммуникации", "Оформление", "Оплата"))

            listHouseBox.apply {
                showListHouse(false)
            }

            showListHouseBox.setOnClickListener {
                showListHouse(true)
            }

            collapseListHouseBox.setOnClickListener {
                showListHouse(false)
            }

            whiteButtonAdvantagesRV.adapter = AdvantagesAdapter(requireContext(), listOf("Бассейн", "Школа", "Детский сад", "Бассейн", "Школа", "Детский сад"))

//            mapView.onCreate(savedInstanceState?.getBundle())

           mapView.getMapAsync { googleMap ->
               map = googleMap
               Log.e("tag", "card")
               val positions = LatLng(55.75222, 37.61556)

               googleMap.apply {
                   addMarker(MarkerOptions().position(positions).title("Marker in Sydney"))
                   moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 10f))
               }
           }

            RealtyServiceImpl().getHouseCatalog { data, e ->
                similarObjects.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = SeenHousesAdapter(requireContext(), listOf(data?.get(0), data?.get(1))) {
                        findNavController().navigate(R.id.action_navigation_home_to_houseFragment)
                    }
                }
            }

            houseBackIcon.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun showListHouse(full: Boolean){
        houseObjectBinding?.apply {
            if (full) {

                listHouseBox.apply {
                    showListHouseBox.visibility = View.GONE
                    collapseListHouseBox.visibility = View.VISIBLE
                    layoutManager = GridLayoutManager(context, 3)
                    adapter = ListHouseBoxAdapter(requireContext(), listOf("", "", "", "", "", "", "", "", "", "", "", "", ""))
                }
            }else{
                listHouseBox.apply {
                    showListHouseBox.visibility = View.VISIBLE
                    collapseListHouseBox.visibility = View.GONE
                    layoutManager = GridLayoutManager(context, 3)
                    adapter = ListHouseBoxAdapter(requireContext(), listOf("", "", "", "", "", ""))
                }
            }
        }
    }

    override fun onDestroy() {
        houseObjectBinding = null
        super.onDestroy()
    }
}