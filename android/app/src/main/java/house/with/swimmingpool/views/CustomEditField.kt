package house.with.swimmingpool.views

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import house.with.swimmingpool.R

class CustomEditField(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var fieldView: EditText? = null
    private var titleView: TextView? = null
    private var hintView: TextView? = null
    private var clearIcon: ImageView? = null
    private var dividerView: View? = null

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_edit_field, this)

        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.CustomEditField, 0, 0)

        fieldView = findViewById(R.id.field)
        titleView = findViewById(R.id.title)
        hintView = findViewById(R.id.hint)
        clearIcon = findViewById(R.id.imageView12)
        dividerView = findViewById(R.id.divider)
        clearIcon?.visibility = View.GONE

        val isNeedShowClearButton =
            typedArray.getBoolean(R.styleable.CustomEditField_need_show_clear_button, false)
        val isButtonMode =
            typedArray.getBoolean(R.styleable.CustomEditField_buttonMode, false)
        val isPassword =
            typedArray.getBoolean(R.styleable.CustomEditField_passwordMode, false)

        if (isPassword) {
            fieldView?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        fieldView?.onFocusChangeListener = OnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                dividerView?.setBackgroundColor(Color.parseColor("#A1A1A1"))
            } else {
                dividerView?.setBackgroundColor(Color.parseColor("#E6E6E6"))
            }
        }

        fieldView?.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                titleView?.hide()
                hintView?.show()
                clearIcon?.hide()
            } else {
                titleView?.show()
                hintView?.hide()

                if (isNeedShowClearButton) {
                    clearIcon?.show()
                }
            }
        }

        if (isButtonMode) {
            setOnClickListener {
                _onClickListener?.invoke()
            }
        }

        clearIcon?.setOnClickListener {
            fieldView?.text?.clear()
        }

        fieldView?.setText("")

        titleView?.text = typedArray.getString(R.styleable.CustomEditField_title)
        hintView?.text = typedArray.getString(R.styleable.CustomEditField_title)

    }

    var value: String?
        get() = fieldView?.text?.toString()
        set(value) { fieldView?.setText(value) }

    var title: String?
        get() = titleView?.text?.toString()
        set(value) {
            titleView?.text = value
            hintView?.text = value
        }

    fun clearError() {
        dividerView?.setBackgroundColor(Color.parseColor("#E6E6E6"))
    }

    fun setError() {
        dividerView?.setBackgroundColor(Color.parseColor("#DB5249"))
    }

    var isPasswordMode
        get() = fieldView?.inputType
        set(value) { fieldView?.inputType }

    private var _onClickListener: (() -> Unit)? = null

    fun addOnClickListener(listener: () -> Unit) {
        _onClickListener = listener
    }

    private fun View.show() {
        visibility = View.VISIBLE
    }

    private fun View.hide() {
        visibility = View.GONE
    }

}