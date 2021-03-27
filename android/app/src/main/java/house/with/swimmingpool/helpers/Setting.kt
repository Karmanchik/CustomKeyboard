package house.with.swimmingpool.helpers

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import house.with.swimmingpool.models.HouseCatalogData

class Setting(ctx: Context) {

    private val pref by lazy { ctx.getSharedPreferences("setting", Context.MODE_PRIVATE) }

    var token: String?
        get() = pref.getString(Keys.TOKEN, null)
        set(value) = pref.edit { putString(Keys.TOKEN, value) }

    val isAuth: Boolean
        get() = token != null

    var houses: List<HouseCatalogData>
        get() = pref.getStringSet(Keys.HOUSE_LIST, setOf())!!.map { Gson().fromJson(it, HouseCatalogData::class.java) }
        set(value) = pref.edit { putStringSet(Keys.HOUSE_LIST, value.map { Gson().toJson(it) }.toSet()) }

    object Keys {
        const val TOKEN = "TOKEN"
        const val HOUSE_LIST = "HOUSE_LIST"
    }

}