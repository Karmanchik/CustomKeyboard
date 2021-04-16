package house.with.swimmingpool.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import house.with.swimmingpool.MainActivity
import house.with.swimmingpool.R

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

fun ImageView.setBannerClick(viewToAnim: View, ctx: Context, function: () -> Unit){
    this.setOnClickListener {
        viewToAnim.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.banner_anim))
        function()
    }
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(activity, message, duration).show()

fun Fragment.toast(@StringRes messageId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(activity, messageId, duration).show()

inline fun <reified A : Activity> Context.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

inline fun <reified A : Activity> Fragment.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(requireContext(), A::class.java).apply(configIntent))
}

fun CharSequence.noDots(): String {
    var textWithoutDots = ""
    this.forEach {
        if (it != '•') {
            textWithoutDots += it
        }
    }
    return textWithoutDots
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

fun EditText.setMaxLength(maxLength: Int) {

    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

}

fun Fragment.navigate(
    fragment: Fragment, bundle: Bundle? = null,
    @AnimRes inAnim: Int? = null,
    @AnimRes outAnim: Int? = null
) {
    try {
        (requireActivity() as MainActivity).showFragment(fragment, bundle, inAnim, outAnim)
    } catch (e: Exception) {
        Log.e("navigation", "error", e)
    }
}

fun Fragment.back() {
    try {
        (requireActivity() as MainActivity).back()
    } catch (e: Exception) {
        Log.e("navigation", "error", e)
    }
}

fun Fragment.showModeFab(isShow: Boolean) {
    try {
        (requireActivity() as MainActivity).showModeFab(isShow)
    } catch (e: Exception) {
        Log.e("navigation", "showModeFab error", e)
    }
}

fun Fragment.showModeBottomMenu(isShow: Boolean) {
    try {
        (requireActivity() as MainActivity).showModeBottomMenu(isShow)
    } catch (e: Exception) {
        Log.e("navigation", "showModeBottomMenu error", e)
    }
}