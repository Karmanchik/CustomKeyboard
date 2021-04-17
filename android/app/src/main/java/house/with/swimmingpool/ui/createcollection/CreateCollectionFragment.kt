package house.with.swimmingpool.ui.createcollection

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.RangeSaveFilterBinding
import house.with.swimmingpool.ui.toast

class CreateCollectionFragment(val onCreated: () -> Unit) : BottomSheetDialogFragment() {

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
        binding.title.text = "Создание подборки"

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


                RealtyServiceImpl().createCollection(name.text.toString(), "") { data, e ->
                    if (e == null) {
                        onCreated.invoke()
                        dismiss()
                    } else {
                        toast("Ошибка")
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(onCreated: () -> Unit) = CreateCollectionFragment(onCreated)
    }

}