package house.with.swimmingpool.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import java.text.SimpleDateFormat
import java.util.*

fun EditText.setRightIcon(@DrawableRes id: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        null, null,
        ContextCompat.getDrawable(context, id), null
    )
}

fun EditText.removeRightIcon() {
    setCompoundDrawablesWithIntrinsicBounds(
        null, null, null, null
    )
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun Fragment.hideKeyboard() {
    requireActivity().apply {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
        }
    }
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(activity, message, duration).show()

fun Fragment.toast(@StringRes messageId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(activity, messageId, duration).show()

fun Context.getDateFromDateDialog(action: (selectedDate: Date, formattedDate: String) -> Unit) {
    val calendar = Calendar.getInstance()
    val selectCalendar = Calendar.getInstance()
    val d =
        DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            selectCalendar.set(Calendar.YEAR, year)
            selectCalendar.set(Calendar.MONTH, monthOfYear)
            selectCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val formatDate = "" + selectCalendar.get(Calendar.DAY_OF_MONTH).formatted() +
                    "" + (selectCalendar.get(Calendar.MONTH) + 1).formatted() +
                    "" + selectCalendar.get(Calendar.YEAR)
            action(selectCalendar.time, formatDate)
        }

    DatePickerDialog(
        this,
        d,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.maxDate = Calendar.getInstance().timeInMillis
    }
        .show()
}

private fun Int.formatted() = if (this < 10) "0$this" else this.toString()

@SuppressLint("SimpleDateFormat")
fun String.reformatDate(inputPattern: String, outputPattern: String): String? {
    val inputFormatter = SimpleDateFormat(inputPattern)
    val outputFormatter = SimpleDateFormat(outputPattern)

    return inputFormatter.parse(this)?.let { outputFormatter.format(it) }
}

fun TextView.setColorText(@ColorRes id: Int) {
    setTextColor(ContextCompat.getColor(context, id))
}

inline fun <reified A : Activity> Context.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

inline fun <reified A : Activity> Fragment.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(requireContext(), A::class.java).apply(configIntent))
}

fun CharSequence.noAsterisks(): String {
    var textWithoutAsterisks = ""
    this.forEach {
        if (it != '*') {
            textWithoutAsterisks += it
        }
    }
    return textWithoutAsterisks
}

fun ImageView.load(url: String?, @DrawableRes stub: Int = R.drawable.placeholder) {
    Glide.with(context)
        .load(url)
        .error(stub)
        .dontAnimate()
        .placeholder(stub)
        .into(this)
}

fun ImageView.load(url: String?, @DrawableRes placeholder: Int, @DrawableRes error: Int) {
    Glide.with(context)
        .load(url)
        .error(error)
        .dontAnimate()
        .placeholder(placeholder)
        .into(this)
}