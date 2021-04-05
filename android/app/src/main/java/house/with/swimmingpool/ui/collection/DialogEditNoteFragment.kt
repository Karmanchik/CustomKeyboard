package house.with.swimmingpool.ui.collection

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.RangeSaveFilterBinding
import house.with.swimmingpool.ui.toast

class DialogEditNoteFragment(
    private val text: String? = null,
    private val onEnterText: (String) -> Unit
) : BottomSheetDialogFragment() {

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

        binding.apply {
            title.text = if (text == null) "Создание заметки" else "Редактирование заметки"
            deleteNote.visibility = if (text == null) View.GONE else View.VISIBLE

            closeIcon.setOnClickListener { dismiss() }
            name.doOnTextChanged { text, _, _, _ ->
                counter.text = "${text?.length} из 350"
            }
            send.setOnClickListener {
                if (name.text.toString().trim().isEmpty()) {
                    toast("Введите название")
                    return@setOnClickListener
                }

                onEnterText.invoke(name.text.toString())
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(text: String?, onEnterText: (String) -> Unit) =
            DialogEditNoteFragment(text, onEnterText)
    }

}