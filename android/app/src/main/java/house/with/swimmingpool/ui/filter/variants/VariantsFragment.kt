package house.with.swimmingpool.ui.filter.variants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentVariantsBinding

class VariantsFragment(
        val title: String,
        val items: List<Pair<String, Boolean>>,
        val onItemsSelected: (List<Pair<String, Boolean>>) -> Unit
) : BottomSheetDialogFragment() {

    lateinit var binding : FragmentVariantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentVariantsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            titleView.text = title
            closeIcon.setOnClickListener { dismiss() }
            variantsRV.adapter = VariantsAdapter(items.toMutableList(), requireContext())
            done.setOnClickListener {
                onItemsSelected.invoke((variantsRV.adapter as VariantsAdapter).items)
                dismiss()
            }
            cancel.setOnClickListener { dismiss() }
        }
    }

    companion object {
        fun newInstance(title: String, items: List<Pair<String, Boolean>>, onItemsSelected: (List<Pair<String, Boolean>>) -> Unit): VariantsFragment {
            return VariantsFragment(title, items, onItemsSelected)
        }
    }

}