package house.with.swimmingpool.helpers

import android.content.Context
import androidx.core.content.edit

class Setting(ctx: Context) {

    private val pref by lazy { ctx.getSharedPreferences("setting", Context.MODE_PRIVATE) }

    var token: String?
        get() = pref.getString(Keys.TOKEN, null)
        set(value) = pref.edit { putString(Keys.TOKEN, value) }

    val isAuth: Boolean
        get() = token != null

    object Keys {
        const val TOKEN = "TOKEN"
    }

}