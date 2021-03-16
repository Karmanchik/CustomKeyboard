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
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentFilterFullBinding
import house.with.swimmingpool.ui.filter.range.RangeDialogFragment
import house.with.swimmingpool.ui.filter.variants.VariantsFragment
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.removeRightIcon
import house.with.swimmingpool.ui.setRightIcon

class FullFilterFragment : Fragment() {

    lateinit var binding : FragmentFilterFullBinding
    private lateinit var viewModel: FullFilterViewModel
    private var isOptionSelected = 0

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
                area.text?.clear()
                price.text?.clear()
                square.text?.clear()
                docType.text?.clear()
                moneyType.text?.clear()
                style.text?.clear()
                sea.text?.clear()
                houseType.text?.clear()
            }

            area.initEditText()
            price.initEditText()
            square.initEditText()
            docType.initEditText()
            moneyType.initEditText()
            style.initEditText()
            sea.initEditText()
            houseType.initEditText()

            moneyType.setOnClickListener {
                VariantsFragment().newInstance().show(parentFragmentManager, "VariantsFragment")
            }
            area.setOnClickListener {
                RangeDialogFragment().newInstance().show(parentFragmentManager, "range")
            }
        }
    }

    fun EditText.initEditText() {
        doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) setRightIcon(R.drawable.ic_clear_field)
            else removeRightIcon()
        }
        onRightDrawableClicked { text.clear() }
    }

    fun onChipClicked(it: View) {
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