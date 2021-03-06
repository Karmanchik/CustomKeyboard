package house.with.swimmingpool.ui.filter.full

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFilterFullBinding
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.filter.range.RangeDialogFragment
import house.with.swimmingpool.ui.filter.variants.VariantsFragment
import house.with.swimmingpool.ui.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FullFilterFragment : Fragment() {

    lateinit var binding: FragmentFilterFullBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(App.setting.filterConfig != null) {
            showFilter(App.setting.filterConfig!!)
        }

        binding.apply {

            segmentedControl.setTypeFace(
                ResourcesCompat.getFont(requireContext().applicationContext, R.font.lato_regular)
            )

            closeIcon.setOnClickListener {
                back()
            }
            showCatalogButton.setOnClickListener {
                navigate(CatalogFragment())
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
                chipGroup.children.forEach {
                    if (it.tag == "2") it.performClick()
                }
                App.setting.filterConfig = null
                districtsFilter = null
                registrationTypeFilter = null
                paymentTypeFilter = null
                interiorFilter = null
                buildingClassFilter = null
                selectedSquareRange = null
                selectedPriceRange = null
                load()
            }

            area.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            area.setOnClickListener {
                if (districtsFilter == null) {
                    districtsFilter = districtsVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants("??????????", districtsFilter!!) {
                    districtsFilter = it
                    area.setSpannable(
                        it.filter { it.second }
                            .joinToString(", ") { it.first }
                            .toSpannableString((requireActivity().windowManager.defaultDisplay.width - 76) / 21)
                    )
                    load()
                }
            }

            docType.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            docType.setOnClickListener {
                if (registrationTypeFilter == null) {
                    registrationTypeFilter =
                        registrationTypeVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants("????????????????????", registrationTypeFilter!!) {
                    registrationTypeFilter = it
                    docType.setSpannable(
                        it.filter { it.second }
                            .joinToString(", ") { it.first }
                            .toSpannableString((requireActivity().windowManager.defaultDisplay.width - 76) / 21)
                    )
                    load()
                }
            }

            moneyType.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            moneyType.setOnClickListener {
                if (paymentTypeFilter == null) {
                    paymentTypeFilter = paymentTypeVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants("?????????? ????????????", paymentTypeFilter!!) {
                    paymentTypeFilter = it
                    moneyType.setSpannable(
                        it.filter { it.second }
                            .joinToString(", ") { it.first }
                            .toSpannableString((requireActivity().windowManager.defaultDisplay.width - 76) / 21)
                    )
                    load()
                }
            }

            style.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            style.setOnClickListener {
                if (interiorFilter == null) {
                    interiorFilter = interiorVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants("??????????????", interiorFilter!!) {
                    interiorFilter = it
                    style.setSpannable(
                        it.filter { it.second }
                            .joinToString(", ") { it.first }
                            .toSpannableString((requireActivity().windowManager.defaultDisplay.width - 76) / 21)
                    )
                    load()
                }
            }

            houseType.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            houseType.setOnClickListener {
                if (buildingClassFilter == null) {
                    buildingClassFilter =
                        buildingClassVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants("?????????? ????????", buildingClassFilter!!) {
                    buildingClassFilter = it
                    houseType.setSpannable(
                        it.filter { it.second }
                            .joinToString(", ") { it.first }
                            .toSpannableString((requireActivity().windowManager.defaultDisplay.width - 76) / 21)
                    )
                    load()
                }
            }

            sea.setOnClickListener {}

            square.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            square.setOnClickListener {
                openRange(
                    getSquareRange,
                    "??????????????, ??2",
                    selectedSquareRange?.first ?: getSquareRange.first,
                    selectedSquareRange?.second ?: getSquareRange.second
                ) { min, max ->
                    square.value = "???? $min ???? $max ??2."
                    selectedSquareRange = Pair(min, max)
                    load()
                }
            }

            price.onClearButtonClicked = {
                districtsFilter = null
                load()
            }
            price.setOnClickListener {
                openRange(
                    getPriceRange,
                    "????????, ??????.",
                    selectedPriceRange?.first ?: getPriceRange.first,
                    selectedPriceRange?.second ?: getPriceRange.second
                ) { min, max ->
                    price.value = "???? $min ???? $max ??????."
                    selectedPriceRange = Pair(min, max)
                    load()
                }
            }

            segmentedControl.setSelectedSegment(
                when (App.setting.filterConfig?.types ?: "house") {
                    "complex" -> 2
                    "flat" -> 1
                    else -> 0
                }
            )
            segmentedControl.addOnSegmentClickListener {
                segmentId = it.column
                load()
            }
        }
        load()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                RealtyServiceImpl().getParamsForFilter()?.data?.let {
                    launch(Dispatchers.Main) {
                        filterConfig = it
                        App.setting.filterVariants = it
                        App.setting.filterConfig?.let { showFilter(it) }
                    }
                }
            } catch (e: Exception) {
                Log.e("test", "load", e)
            }
        }
    }

    private fun String.toSpannableString(count: Int): SpannableString {
        if (length <= count) return SpannableString(this)

        var text = this.take(count - 10)

        val inputCount = filter { it == ',' }.length
        val outputCount = text.filter { it == ',' }.length

        text += "??? + ${inputCount - outputCount + 1} ??????"

        return SpannableString(text).apply {
            setSpan(
                ForegroundColorSpan(Color.BLUE),
                text.lastIndex - 8,
                text.lastIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
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
        title: String,
        list: List<Pair<String, Boolean>>,
        onItemsSelected: (List<Pair<String, Boolean>>) -> Unit
    ) {
        VariantsFragment.newInstance(title, list, onItemsSelected)
            .show(parentFragmentManager, "VariantsFragment")
    }

    var segmentId: Int = 0

    @SuppressLint("SetTextI18n")
    private fun load() {

        val lol: String = when (segmentId) {
            0 -> "house"
            1 -> "flat"
            else -> "complex"
        }
        val filter = FilterObjectsRequest(
            types = listOf(lol),

            // ????????
            districts = binding.area.value?.split(", ")
                ?.mapNotNull { value -> districtsVariants?.entries?.firstOrNull { it.value == value }?.key },

            // ????????
            price_all_from = selectedPriceRange?.first?.toString(),
            price_all_to = selectedPriceRange?.second?.toString(),

            // ??????????????
            square_all_from = selectedSquareRange?.first?.toString(),
            square_all_to = selectedSquareRange?.second?.toString(),

            // ????????????????????
            registrationTypes = binding.docType.value?.split(", ")
                ?.mapNotNull { value -> registrationTypeVariants?.entries?.firstOrNull { it.value == value }?.key },

            // ?????????? ????????????
            paymentTypes = binding.moneyType.value?.split(", ")
                ?.mapNotNull { value -> paymentTypeVariants?.entries?.firstOrNull { it.value == value }?.key },

            // ??????????????
            interiorTypes = binding.style.value?.split(", ")
                ?.mapNotNull { value -> interiorVariants?.entries?.firstOrNull { it.value == value }?.key },

            // ?????????? ????????
            buildingClass = binding.houseType.value?.split(", ")
                ?.mapNotNull { value -> buildingClassVariants?.entries?.firstOrNull { it.value == value }?.key },

            // ????????
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

                if (data.isNotEmpty()) {
                    binding.showCatalogButton.text = "???????????????? ${data.size} ??????????????????????"
                    binding.showCatalogButton.isEnabled = true
                } else {
                    binding.showCatalogButton.text = "???????????? ???? ??????????????"
                    binding.showCatalogButton.isEnabled = false
                }

                App.setting.houses = data

                binding.showCatalogButton.setOnClickListener {
                    navigate(CatalogFragment())
                }
            } else {
                binding.showCatalogButton.isEnabled = false
                binding.showCatalogButton.text = "?????? ????????????????"
                binding.showCatalogButton.setOnClickListener(null)
            }
        }
    }

    private fun showFilter(filter: FilterObjectsRequest) {
        binding.segmentedControl.setSelectedSegment(
            when (filter.types?.firstOrNull()) {
                "house" -> 0
                "flat" -> 1
                else -> 2
            }
        )

        binding.area.value = filter.districts
            ?.mapNotNull { districtsVariants?.get(it) }
            ?.joinToString(", ")

        filter.price_all_from?.let {
            selectedPriceRange = Pair(
                filter.price_all_from?.toIntOrNull() ?: 0,
                filter.price_all_to?.toIntOrNull() ?: 0
            )
            binding.price.value = "???? ${filter.price_all_from} ???? ${filter.price_all_to} ??????."
        }

        filter.square_all_from?.let {
            selectedSquareRange = Pair(
                filter.square_all_from?.toIntOrNull() ?: 0,
                filter.square_all_to?.toIntOrNull() ?: 0
            )
            binding.square.value = "???? ${filter.square_all_from} ???? ${filter.square_all_to} ??2."
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