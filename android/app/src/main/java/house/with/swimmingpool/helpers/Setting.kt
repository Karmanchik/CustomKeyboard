package house.with.swimmingpool.helpers

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.User
import house.with.swimmingpool.models.request.FilterObjectsRequest

class Setting(ctx: Context) {

    private val pref by lazy { ctx.getSharedPreferences("setting", Context.MODE_PRIVATE) }

    var token: String?
        get() = pref.getString(Keys.TOKEN, null)
        set(value) = pref.edit { putString(Keys.TOKEN, value) }

    var user: User?
        get() = Gson().fromJson(pref.getString(Keys.USER, null), User::class.java)
        set(value) = pref.edit { putString(Keys.USER, Gson().toJson(value)) }

    val phone get() = user?.login

    val apiToken: String? get() = token?.let { "Bearer $it" }

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

    var filterConfig: FilterObjectsRequest?
        get() = Gson().fromJson(pref.getString(Keys.FILTER, null), FilterObjectsRequest::class.java)
        set(value) = pref.edit { putString(Keys.FILTER, Gson().toJson(value)) }

    var filterVariants: JsonObject?
        get() = Gson().fromJson(pref.getString(Keys.FILTER_VARIANTS, null), JsonObject::class.java)
        set(value) = pref.edit { putString(Keys.FILTER_VARIANTS, Gson().toJson(value)) }

    var tmpObj: HouseCatalogData?
        get() = Gson().fromJson(pref.getString(Keys.TMP_OBJECT, null), HouseCatalogData::class.java)
        set(value) = pref.edit { putString(Keys.TMP_OBJECT, Gson().toJson(value)) }

    fun getObjectById(id: Int): HouseCatalogData? =
        App.database?.eventsDao()?.getById(id)

    object Keys {
        const val USER = "USER"
        const val TOKEN = "TOKEN"
        const val FILTER = "FILTER"
        const val FILTER_VARIANTS = "FILTER_VARIANTS"
        const val TMP_OBJECT = "TMP_OBJECT"
    }

}