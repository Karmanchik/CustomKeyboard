package house.with.swimmingpool.ui.filter.range

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.appyvet.materialrangebar.RangeBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.DialogRangeBinding

class RangeDialogFragment(
    private val range1: Pair<Int, Int>,
    private val title1: String,
    private val selectedMinValue1: Int,
    private val selectedMaxInt1: Int,
    private val onEnter: (min: Int, max: Int) -> Unit
) : BottomSheetDialogFragment() {

    lateinit var binding: DialogRangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRangeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            closeIcon.setOnClickListener { dismiss() }
            cancel.setOnClickListener { dismiss() }

            title.text = title1

            done.setOnClickListener {
                onEnter.invoke(
                    range.leftIndex.toValue(),
                    range.rightIndex.toValue()
                )
                dismiss()
            }

            min.setText(selectedMinValue1.toString().addDividers())
            max.setText(selectedMaxInt1.toString().addDividers())

            try {
                range.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
                    override fun onRangeChangeListener(
                        rangeBar: RangeBar?,
                        leftPinIndex: Int,
                        rightPinIndex: Int,
                        leftPinValue: String?,
                        rightPinValue: String?
                    ) {
                        min.setText(leftPinIndex.toValue().toString().addDividers())
                        max.setText(rightPinIndex.toValue().toString().addDividers())
                    }

                    override fun onTouchStarted(rangeBar: RangeBar?) = Unit

                    override fun onTouchEnded(rangeBar: RangeBar?) = Unit

                })

                range.setRangePinsByIndices(
                    selectedMinValue1.toIndex(),
                    selectedMaxInt1.toIndex()
                )
            } catch (e: Exception) {
                Log.e("test", "range init", e)
            }
        }
    }

    private fun String.addDividers(): String {
        return reversed()
            .toList()
            .chunked(3)
            .map { it.joinToString("") }
            .joinToString(" ")
            .reversed()
    }

    private fun Int.toValue(): Int = (this) * k + range1.first

    private fun Int.toIndex(): Int = (this - range1.first) / k

    private val k = module / 100

    private val module get() = range1.second - range1.first

    companion object {
        fun newInstance(
            range: Pair<Int, Int>,
            title: String,
            selectedMinValue: Int,
            selectedMaxInt: Int,
            onEnter: (min: Int, max: Int) -> Unit
        ): RangeDialogFragment {
            return RangeDialogFragment(range, title, selectedMinValue, selectedMaxInt, onEnter)
        }
    }

}