package house.with.swimmingpool.ui.filter.variants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentVariantsBinding

class VariantsFragment() : BottomSheetDialogFragment() {

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
            closeIcon.setOnClickListener { dismiss() }
            variantsRV.adapter = VariantsAdapter(listOf(
                    Pair("", false),
                    Pair("", false),
                    Pair("", false),
                    Pair("", false)
            ), requireContext())
        }
    }

    fun newInstance(): VariantsFragment {
        return VariantsFragment()
    }

}