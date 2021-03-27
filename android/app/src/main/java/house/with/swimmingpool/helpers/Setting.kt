package house.with.swimmingpool.helpers

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import house.with.swimmingpool.App
import house.with.swimmingpool.models.HouseCatalogData

class Setting(ctx: Context) {

    private val pref by lazy { ctx.getSharedPreferences("setting", Context.MODE_PRIVATE) }

    var token: String?
        get() = pref.getString(Keys.TOKEN, null)
        set(value) = pref.edit { putString(Keys.TOKEN, value) }

    val isAuth: Boolean
        get() = token != null

    var houses: List<HouseCatalogData>
        get() = App.database?.eventsDao()?.getAll() ?: listOf()
        set(value) {
            App.database?.eventsDao()?.let { db ->
                db.getAll().forEach { db.delete(it) }
                value.forEach { db.insert(it) }
            }
        }

    object Keys {
        const val TOKEN = "TOKEN"
        const val HOUSE_LIST = "HOUSE_LIST"
    }

}