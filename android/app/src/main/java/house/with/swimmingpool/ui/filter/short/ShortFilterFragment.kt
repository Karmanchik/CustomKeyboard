package house.with.swimmingpool.ui.filter.short

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentFilterShortBinding
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.removeRightIcon
import house.with.swimmingpool.ui.setRightIcon


class ShortFilterFragment(val onClick: () -> Unit) : BottomSheetDialogFragment() {

    lateinit var binding : FragmentFilterShortBinding
    private lateinit var viewModel: ShortFilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

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
            minEditText.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) minEditText.setRightIcon(R.drawable.ic_clear_field)
                else minEditText.removeRightIcon()
            }
            maxEditText.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) maxEditText.setRightIcon(R.drawable.ic_clear_field)
                else maxEditText.removeRightIcon()
            }
            minEditText.onRightDrawableClicked { minEditText.text.clear() }
            maxEditText.onRightDrawableClicked { maxEditText.text.clear() }
        }
    }

    fun newInstance(onClick: () -> Unit): ShortFilterFragment {
        return ShortFilterFragment(onClick)
    }

}