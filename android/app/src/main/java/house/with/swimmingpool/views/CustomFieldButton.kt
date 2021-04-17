package house.with.swimmingpool.views

import android.content.Context
import android.graphics.Color
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableString
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import house.with.swimmingpool.R

class CustomFieldButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var fieldView: TextView? = null
    private var titleView: TextView? = null
    private var clearIcon: ImageView? = null
    private var dividerView: View? = null

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_button_field, this)

        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.CustomFieldButton, 0, 0)

        fieldView = findViewById(R.id.field)
        titleView = findViewById(R.id.title)
        clearIcon = findViewById(R.id.imageView12)
        dividerView = findViewById(R.id.divider)
        clearIcon?.visibility = View.GONE

        val isNeedShowClearButton =
            typedArray.getBoolean(R.styleable.CustomFieldButton_need_show_clear_butto, false)
        val isButtonMode =
            typedArray.getBoolean(R.styleable.CustomFieldButton_buttonMod, false)
        val isPassword =
            typedArray.getBoolean(R.styleable.CustomFieldButton_passwordMod, false)

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
                clearIcon?.hide()
                titleView?.visibility = View.VISIBLE
            } else {
                titleView?.visibility = View.GONE
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
            fieldView?.text = ""
        }

        titleView?.text = typedArray.getString(R.styleable.CustomFieldButton_titl)

    }

    fun setSpannable(spanString: SpannableString) {
        Log.e("test", spanString.toString())
        fieldView?.text = spanString
    }

    var value: String?
        get() = fieldView?.text?.toString()
        set(value) { fieldView?.setText(value) }

    var title: String?
        get() = titleView?.text?.toString()
        set(value) {
            titleView?.text = value
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