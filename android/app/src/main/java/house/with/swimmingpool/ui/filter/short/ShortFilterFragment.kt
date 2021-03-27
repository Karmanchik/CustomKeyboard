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


        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                RealtyServiceImpl().getParamsForFilter()?.data?.let {
                    launch(Dispatchers.Main) {
                        filterConfig = it

                        binding.range.setOnRangeBarChangeListener(object :
                            RangeBar.OnRangeBarChangeListener {
                            override fun onRangeChangeListener(
                                rangeBar: RangeBar?,
                                leftPinIndex: Int,
                                rightPinIndex: Int,
                                leftPinValue: String?,
                                rightPinValue: String?
                            ) {
                                binding.min.setText(leftPinIndex.toValue().toString())
                                binding.max.setText(rightPinIndex.toValue().toString())
                            }

                            override fun onTouchStarted(rangeBar: RangeBar?) = Unit
                            @SuppressLint("SetTextI18n")
                            override fun onTouchEnded(rangeBar: RangeBar?) {
                                RealtyServiceImpl().getHouseCatalog { data, e ->
                                    if (data != null) {
                                        binding.showButton.isEnabled = true
                                        binding.showButton.text = "Показать ${data.size} предложений"
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
                            min.setText(getPriceRange.first.toString())
                            max.setText(getPriceRange.second.toString())
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("test", "load", e)
            }
        }
    }

    private fun Int.toValue(): Int = (this) * k + getPriceRange.first

    private val k get() = module / 100

    private val module get() = getPriceRange.second - getPriceRange.first

    fun newInstance(onClick: () -> Unit): ShortFilterFragment {
        return ShortFilterFragment(onClick)
    }

}