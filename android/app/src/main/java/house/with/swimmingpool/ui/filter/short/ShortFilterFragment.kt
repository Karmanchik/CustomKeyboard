package house.with.swimmingpool.ui.filter.short

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.appyvet.materialrangebar.RangeBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentFilterShortBinding
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.removeRightIcon
import house.with.swimmingpool.ui.setRightIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ShortFilterFragment(
    private val onClick: () -> Unit
) : BottomSheetDialogFragment() {

    lateinit var binding: FragmentFilterShortBinding
    private lateinit var viewModel: ShortFilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    private var filterConfig: JsonObject? = null
    private val filterCategories get() = filterConfig?.entrySet()?.map { Pair(it.key, it.value) }
    private val getPriceRange
        get() = Pair(
            filterCategories?.firstOrNull { it.first == "minPrice" }?.second?.asInt ?: 0,
            filterCategories?.firstOrNull { it.first == "maxPrice" }?.second?.asInt ?: 0
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterShortBinding.inflate(layoutInflater)
        viewModel = ShortFilterViewModel()
        return binding.root
    }

    private fun String.addDividers(): String {
        return reversed()
            .toList()
            .chunked(3)
            .map { it.joinToString("") }
            .joinToString(" ")
            .reversed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            showButton.setOnClickListener {
                onClick.invoke()
                dismiss()
            }
            closeIcon.setOnClickListener {
                dismiss()
            }

            min.doOnTextChanged { text, _, _, _ ->
                try {
                    if (min.isFocused) {
                        range.setRangePinsByIndices(
                            text.toString().replace(" ", "").toIntOrNull()?.toIndex() ?: 0,
                            range.rightIndex
                        )
                    }
                } catch (e: Exception) {
                    Log.e("test", "2", e)
                }
            }

            max.doOnTextChanged { text, _, _, _ ->
                try {
                    if (max.isFocused) {
                        range.setRangePinsByIndices(
                            range.leftIndex,
                            text.toString().replace(" ", "").toIntOrNull()?.toIndex() ?: 0
                        )
                    }
                } catch (e: Exception) {
                    Log.e("test", "1", e)
                }
            }

        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                RealtyServiceImpl().getParamsForFilter()?.data?.let {
                    launch(Dispatchers.Main) {
                        filterConfig = it

                        binding.range.setOnDragListener { view, dragEvent ->
                            binding.range.requestFocus()
                        }

                        binding.range.setOnRangeBarChangeListener(object :
                            RangeBar.OnRangeBarChangeListener {
                            override fun onRangeChangeListener(
                                rangeBar: RangeBar?,
                                leftPinIndex: Int,
                                rightPinIndex: Int,
                                leftPinValue: String?,
                                rightPinValue: String?
                            ) {
                                try {
                                    if (!binding.min.isFocused && !binding.max.isFocused) {
                                        binding.min.setText(leftPinIndex.toValue().toString().addDividers())
                                        binding.max.setText(rightPinIndex.toValue().toString().addDividers())
                                    }
                                } catch (e: Exception) {
                                    Log.e("test", "3", e)
                                }
                            }

                            override fun onTouchStarted(rangeBar: RangeBar?) = Unit
                            @SuppressLint("SetTextI18n")
                            override fun onTouchEnded(rangeBar: RangeBar?) {
                                val filter = FilterObjectsRequest(
                                    price_all_from = binding.min.text.toString(),
                                    price_all_to = binding.max.text.toString()
                                )
                                RealtyServiceImpl().getObjectsByFilter(filter) { data, e ->
                                    if (data != null) {
                                        App.setting.filterConfig = filter

                                        if (data.isNotEmpty()) {
                                            binding.showButton.text = "Показать ${data.size} предложений"
                                            binding.showButton.isEnabled = true
                                        } else {
                                            binding.showButton.text = "Ничего не найдено"
                                            binding.showButton.isEnabled = false
                                        }

                                        binding.showButton.setOnClickListener {
                                            App.setting.houses = data
                                            findNavController().navigate(R.id.action_shortFilterFragment_to_catalogViewModel)
                                        }
                                    } else {
                                        binding.showButton.isEnabled = false
                                        binding.showButton.text = "Нет объектов"
                                        binding.showButton.setOnClickListener(null)
                                    }
                                }
                            }
                        })

                        binding.apply {
                            min.setText(getPriceRange.first.toString().addDividers())
                            max.setText(getPriceRange.second.toString().addDividers())
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("test", "load", e)
            }
        }
    }

    private fun Int.toValue(): Int = (this) * k + getPriceRange.first

    private fun Int.toIndex(): Int = (this - getPriceRange.first) / k

    private val k get() = module / 100

    private val module get() = getPriceRange.second - getPriceRange.first

    fun newInstance(onClick: () -> Unit): ShortFilterFragment {
        return ShortFilterFragment(onClick)
    }

}