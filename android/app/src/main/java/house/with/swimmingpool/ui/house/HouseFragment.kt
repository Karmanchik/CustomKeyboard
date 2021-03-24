package house.with.swimmingpool.ui.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.MainTags
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter
import house.with.swimmingpool.ui.house.adapters.HouseHeaderAdapter
import house.with.swimmingpool.ui.house.adapters.WhiteButtonAdapter

class HouseFragment : Fragment() {

    private var houseObjectBinding: FragmentHouseBinding? = null

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

            whiteButtonRV.adapter = WhiteButtonAdapter(requireContext(), listOf("Общие", "Коммуникации", "Оформление", "Оплата"))

            whiteButtonFilterRV.adapter = WhiteButtonAdapter(requireContext(), listOf("Главная галерея", "Декабрь 2020", "Февраль 2020"))
        }
    }

    override fun onDestroy() {
        houseObjectBinding = null
        super.onDestroy()
    }
}