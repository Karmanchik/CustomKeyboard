package house.with.swimmingpool.ui.filter.full

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFilterFullBinding
import house.with.swimmingpool.ui.filter.range.RangeDialogFragment
import house.with.swimmingpool.ui.filter.variants.VariantsFragment
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.removeRightIcon
import house.with.swimmingpool.ui.setRightIcon
import house.with.swimmingpool.views.CustomEditField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FullFilterFragment : Fragment() {

    lateinit var binding: FragmentFilterFullBinding
    private lateinit var viewModel: FullFilterViewModel
    private var isOptionSelected = 0

    private var filterConfig: JsonObject? = null
    private val filterCategories get() = filterConfig?.entrySet()?.map { Pair(it.key, it.value) }

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
                }
            }

            moneyType.setOnClickListener {
                if (paymentTypeFilter == null) {
                    paymentTypeFilter = paymentTypeVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(paymentTypeFilter!!) {
                    paymentTypeFilter = it
                    moneyType.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
                }
            }

            style.setOnClickListener {
                if (interiorFilter == null) {
                    interiorFilter = interiorVariants?.entries?.map { Pair(it.value, false) }
                }

                openVariants(interiorFilter!!) {
                    interiorFilter = it
                    style.value = (it.filter { it.second }.map { it.first }.joinToString(", "))
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
                }
            }

            sea.setOnClickListener {
                openRange()
            }

            price.setOnClickListener {
                openRange()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                RealtyServiceImpl().getParamsForFilter()?.data?.let {
                    launch(Dispatchers.Main) {
                        filterConfig = it
                    }
                }
            } catch (e: Exception) {
                Log.e("test", "load", e)
            }
        }
    }

    private fun openRange() {
        RangeDialogFragment().newInstance().show(parentFragmentManager, "range")
    }

    private fun openVariants(
        list: List<Pair<String, Boolean>>,
        onItemsSelected: (List<Pair<String, Boolean>>) -> Unit
    ) {
        VariantsFragment.newInstance(list, onItemsSelected)
            .show(parentFragmentManager, "VariantsFragment")
    }

    private fun onChipClicked(it: View) {
        if (it.tag as String == "1") {
            it.setBackgroundResource(R.drawable.selected_chip)
            (it as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            it.tag = "2"
            isOptionSelected++
        } else {
            it.setBackgroundResource(R.drawable.unselected_chip)
            (it as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            it.tag = "1"
            isOptionSelected--
        }
        Log.e("options", isOptionSelected.toString())
        binding.showCatalogButton.isEnabled = isOptionSelected == 0
    }

}