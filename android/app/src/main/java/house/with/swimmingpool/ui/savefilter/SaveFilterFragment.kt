package house.with.swimmingpool.ui.savefilter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.RangeSaveFilterBinding
import house.with.swimmingpool.ui.toast


class SaveFilterFragment : BottomSheetDialogFragment() {

    lateinit var binding: RangeSaveFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RangeSaveFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.requestFocus()

        binding.apply {
            closeIcon.setOnClickListener { dismiss() }
            name.doOnTextChanged { text, _, _, _ ->
                counter.text = "${text?.length} из 100"
            }
            send.setOnClickListener {
                if (name.text.toString().trim().isEmpty()) {
                    toast("Введите название")
                    return@setOnClickListener
                }

                val filter = App.setting.filterConfig ?: return@setOnClickListener

                RealtyServiceImpl().addSearch(name.text.toString(), filter) { data, e ->
                    if (e == null) {
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = SaveFilterFragment()
    }

}