package house.with.swimmingpool.ui.filter.full

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFilterFullBinding
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.filter.range.RangeDialogFragment
import house.with.swimmingpool.ui.filter.variants.VariantsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FullFilterFragment : Fragment() {

    lateinit var binding: FragmentFilterFullBinding
    private lateinit var viewModel: FullFilterViewModel
    private var isOptionSelected = 0

    private var filterConfig: JsonObject? = null
    private val filterCategories get() = filterConfig?.entrySet()?.map { Pair(it.key, it.value) }

    private var selectedPriceRange: Pair<Int, Int>? = null
    private val getPriceRange
        get() = Pair(
            filterCategories?.firstOrNull { it.first == "minPrice" }?.second?.asInt ?: 0,
            filterCategories?.firstOrNull { it.first == "maxPrice" }?.second?.asInt ?: 0
        )

    private var selectedSquareRange: Pair<Int, Int>? = null
    private val getSquareRange
        get() = Pair(
            filterCategories?.firstOrNull { it.first == "minSquare" }?.second?.asInt ?: 0,
            filterCategories?.firstOrNull { it.first == "maxSquare" }?.second?.asInt ?: 0
        )

    private val districtsVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "districts" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val registrationTypeVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "registration_types" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val paymentTypeVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "payment_types" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val interiorVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "interior" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val buildingClassVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "building_class" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private val tagsVariants
        get() = filterCategories
            ?.firstOrNull { it.first == "advantages" }
            ?.second
            ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }

    private var districtsFilter: List<Pair<String, Boolean>>? = null
    private var registrationTypeFilter: List<Pair<String, Boolean>>? = null
    private var paymentTypeFilter: List<Pair<String, Boolean>>? = null
    private var interiorFilter: List<Pair<String, Boolean>>? = null
    private var buildingClassFilter: List<Pair<String, Boolean>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterFullBinding.inflate(layoutInflater)
        viewModel = FullFilterViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            closeIcon.setOnClickListener {
                findNavController().popBackStack()
            }
            showCatalogButton.setOnClickListener {
                findNavController().navigate(R.id.action_fullFilterFragment_to_catalogViewModel)
            }

            chip1.setOnClickListener { onChipClicked(it) }
            chip2.setOnClickListener { onChipClicked(it) }
            chip3.setOnClickListener { onChipClicked(it) }
            chip4.setOnClickListener { onChipClicked(it) }
            chip5.setOnClickListener { onChipClicked(it) }
            chip6.setOnClickListener { onChipClicked(it) }
            chip7.setOnClickListener { onChipClicked(it) }
            chip8.setOnClickListener { onChipClicked(it) }
            chip9.setOnClickListener { onChipClicked(it) }
            chip10.setOnClickListener { onChipClicked(it) }
            chip11.setOnClickListener { onChipClicked(it) }
            chip12.setOnClickListener { onChipClicked(it) }
            chip13.setOnClickListener { onChipClicked(it) }
            chip14.setOnClickListener { onChipClicked(it) }
            chip15.setOnClickListener { onChipClicked(it) }
            chip16.setOnClickListener { onChipClicked(it) }
            chip17.setOnClickListener { onChipClicked(it) }
            chip18.setOnClickListener { onChipClicked(it) }
            chip19.setOnClickListener { onChipClicked(it) }
            chip20.setOnClickListener { onChipClicked(it) }
            chip21.setOnClickListener { onChipClicked(it) }
            chip22.setOnClickListener { onChipClicked(it) }
            chip23.setOnClickListener { onChipClicked(it) }
            chip24.setOnClickListener { onChipClicked(it) }

            resetButton.setOnClickListener {
                area.value = ""
                price.value = ""
                square.value = ""
                docType.value = ""
                moneyType.value = ""
                style.value = ""
                sea.value = ""
                houseType.value = ""
            }

            area.setOnClickListener {
                if (districtsFilter == null) {
                    districtsFilter = districtsVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(districtsFilter!!) {
                    districtsFilter = it
                    area.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
                    load()
                }
            }

            docType.setOnClickListener {
                if (registrationTypeFilter == null) {
                    registrationTypeFilter =
                        registrationTypeVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(registrationTypeFilter!!) {
                    registrationTypeFilter = it
                    docType.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
                    load()
                }
            }

            moneyType.setOnClickListener {
                if (paymentTypeFilter == null) {
                    paymentTypeFilter = paymentTypeVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(paymentTypeFilter!!) {
                    paymentTypeFilter = it
                    moneyType.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
                    load()
                }
            }

            style.setOnClickListener {
                if (interiorFilter == null) {
                    interiorFilter = interiorVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(interiorFilter!!) {
                    interiorFilter = it
                    style.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
                    load()
                }
            }

            houseType.setOnClickListener {
                if (buildingClassFilter == null) {
                    buildingClassFilter =
                        buildingClassVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(buildingClassFilter!!) {
                    buildingClassFilter = it
                    houseType.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
                    load()
                }
            }

            sea.setOnClickListener {
//                openRange()
            }

            square.setOnClickListener {
                openRange(
                    getSquareRange,
                    "Площадь",
                    selectedSquareRange?.first ?: getSquareRange.first,
                    selectedSquareRange?.second ?: getSquareRange.second
                ) { min, max ->
                    square.value = "${min}m2. - ${max}m2."
                    selectedSquareRange = Pair(min, max)
                    load()
                }
            }

            price.setOnClickListener {
                openRange(
                    getPriceRange,
                    "Цена, р.",
                    selectedPriceRange?.first ?: getPriceRange.first,
                    selectedPriceRange?.second ?: getPriceRange.second
                ) { min, max ->
                    price.value = "${min}р. - ${max}р."
                    selectedPriceRange = Pair(min, max)
                    load()
                }
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                RealtyServiceImpl().getParamsForFilter()?.data?.let {
                    launch(Dispatchers.Main) {
                        filterConfig = it
                        App.setting.filterConfig?.let { showFilter(it) }
                    }
                }
            } catch (e: Exception) {
                Log.e("test", "load", e)
            }
        }
    }

    private fun openRange(
        range: Pair<Int, Int>,
        title: String,
        selectedMinValue: Int,
        selectedMaxInt: Int,
        onEnter: (min: Int, max: Int) -> Unit
    ) {
        RangeDialogFragment.newInstance(range, title, selectedMinValue, selectedMaxInt, onEnter)
            .show(parentFragmentManager, "range")
    }

    private fun openVariants(
        list: List<Pair<String, Boolean>>,
        onItemsSelected: (List<Pair<String, Boolean>>) -> Unit
    ) {
        VariantsFragment.newInstance(list, onItemsSelected)
            .show(parentFragmentManager, "VariantsFragment")
    }

    private fun load() {
        val filter = FilterObjectsRequest(
            // раен
            districts = binding.area.value?.split(", ")
                ?.mapNotNull { value -> districtsVariants?.entries?.firstOrNull { it.value == value }?.key },

            // цена
            price_all_from = (selectedPriceRange?.first ?: getPriceRange.first).toString(),
            price_all_to = (selectedPriceRange?.second ?: getPriceRange.second).toString(),

            // площадь
            square_all_from = (selectedSquareRange?.first ?: getSquareRange.first).toString(),
            square_all_to = (selectedSquareRange?.second ?: getSquareRange.second).toString(),

            // оформление
            registrationTypes = binding.docType.value?.split(", ")
                ?.mapNotNull { value -> registrationTypeVariants?.entries?.firstOrNull { it.value == value }?.key },

            // форма оплаты
            paymentTypes = binding.moneyType.value?.split(", ")
                ?.mapNotNull { value -> paymentTypeVariants?.entries?.firstOrNull { it.value == value }?.key },

            // отделка
            interiorTypes = binding.style.value?.split(", ")
                ?.mapNotNull { value -> interiorVariants?.entries?.firstOrNull { it.value == value }?.key },

            // класс дома
            buildingClass = binding.houseType.value?.split(", ")
                ?.mapNotNull { value -> buildingClassVariants?.entries?.firstOrNull { it.value == value }?.key },

            // чипы
            advantages = binding.chipGroup.children
                .filter { it.tag == "2" }
                .mapNotNull {
                    val text = (it as TextView).text.toString()
                    tagsVariants?.entries?.firstOrNull { it.value == text }?.key?.toString()
                }.toList()

        )
        RealtyServiceImpl().getObjectsByFilter(filter) { data, e ->
            if (data != null) {
                App.setting.filterConfig = filter
                binding.showCatalogButton.isEnabled = true
                binding.showCatalogButton.text = "Показать ${data.size} предложений"
                binding.showCatalogButton.setOnClickListener {
                    App.setting.houses = data
                    findNavController().navigate(R.id.action_fullFilterFragment_to_catalogViewModel)
                }
            } else {
                binding.showCatalogButton.isEnabled = false
                binding.showCatalogButton.text = "Нет объектов"
                binding.showCatalogButton.setOnClickListener(null)
            }
        }
    }

    private fun showFilter(filter: FilterObjectsRequest) {
        Log.e("showFilter", Gson().toJson(filter))
        binding.area.value = filter.districts
            ?.mapNotNull { districtsVariants?.get(it) }
            ?.joinToString(", ")

        filter.price_all_from?.let {
            selectedPriceRange = Pair(
                filter.price_all_from?.toIntOrNull() ?: 0,
                filter.price_all_to?.toIntOrNull() ?: 0
            )
            binding.price.value = "${filter.price_all_from}р. - ${filter.price_all_to}р."
        }

        filter.square_all_from?.let {
            selectedSquareRange = Pair(
                filter.square_all_from?.toIntOrNull() ?: 0,
                filter.square_all_to?.toIntOrNull() ?: 0
            )
            binding.square.value = "${filter.square_all_from}m2. - ${filter.square_all_to}m2."
        }

        binding.docType.value = filter.registrationTypes
            ?.mapNotNull { registrationTypeVariants?.get(it) }
            ?.joinToString(", ")

        binding.moneyType.value = filter.paymentTypes
            ?.mapNotNull { paymentTypeVariants?.get(it) }
            ?.joinToString(", ")

        binding.style.value = filter.interiorTypes
            ?.mapNotNull { interiorVariants?.get(it) }
            ?.joinToString(", ")

        binding.houseType.value = filter.buildingClass
            ?.mapNotNull { buildingClassVariants?.get(it) }
            ?.joinToString(", ")

        binding.chipGroup.children.forEach {
            val text = (it as TextView).text.toString()
            val idForText = tagsVariants?.entries?.firstOrNull { it.value == text }?.key
            if (filter.advantages?.any { it == idForText } == true)
                it.performClick()
        }
    }

    private fun onChipClicked(it: View) {
        if (it.tag as String == "1") {
            it.setBackgroundResource(R.drawable.selected_chip)
            (it as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            it.tag = "2"
        } else {
            it.setBackgroundResource(R.drawable.unselected_chip)
            (it as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            it.tag = "1"
        }
        load()
    }

}