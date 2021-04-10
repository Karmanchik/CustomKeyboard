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

    var registerImageLink: String?
        get() = pref.getString("REGISTER_IMAGE", null)
        set(value) = pref.edit { putString("REGISTER_IMAGE", value) }

    var user: User?
        get() = Gson().fromJson(pref.getString(Keys.USER, null), User::class.java)
        set(value) = pref.edit { putString(Keys.USER, Gson().toJson(value)) }

    val phone get() = user?.login //"88889998877"

    val apiToken: String? get() = token?.let { "Bearer $it" }
//    val apiToken: String? get() = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkb21zYmFzc2Vpbm9tLnJ1IiwiaWF0IjoxNjE3NDc0MzI1LCJleHAiOjE2MjAwNjYzMjUsImF1ZCI6ImRvbXNiYXNzZWlub20ucnUifQ.2fwqurWZvJtZ6xwSye7GZ1cOyE4Dno59QEWv3h-wD3w"

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

    var isSearchActivityOpen: Boolean
        get() = pref.getBoolean("ACTIVITY_SEARCH", false)
        set(value) = pref.edit { putBoolean("ACTIVITY_SEARCH", value) }

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