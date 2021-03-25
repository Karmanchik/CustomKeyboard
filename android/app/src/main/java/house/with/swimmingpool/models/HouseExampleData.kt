package house.with.swimmingpool.models

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject


data class HouseExampleData(
    val analogs: List<HouseCatalogData>?,
//    val children: List<Children>?,
    val communications: JsonObject?,
    val advantages : List<String>?,
    val description: String?,
    val discount: Int?,
    val editedon: String?,
    val galleries: JsonObject?,
    val general: JsonObject?,
    val geolocation: Geolocation?,
    val hits: Int?,
    val icon: String?,
    val id: Int?,
    val isFavourite: Boolean?,
    val location: String?,
    val mainTags: List<MainTags>?,
//    val note: String?,
    val payment: JsonObject?,
    val price: String?,
    val price_of_one_meter: String?,
    val promo: String,
    val registration: JsonObject?,
    val statuses: List<String>?,
    val title: String?,
    val type: String?,
    val video: List<String?>
) {

    fun getGallery(): List<Pair<String, List<Img>>>? {
        return galleries
                ?.entrySet()
                ?.map {
                    val value = it.value.asJsonArray.map { Gson().fromJson(it, Img::class.java) }//  Gson().fromJson(it.value.as, List<Img>::class.java)
                    Pair(it.key, value)
                }
    }

    fun formattedCommunications(): Map<String, String>? {
        return communications?.entrySet()?.map { Pair(it.key, it.value.toString()) }?.toMap()
    }

    fun formattedGalleries(): Map<String, JsonElement>? {
        return galleries?.entrySet()?.map { Pair(it.key, it.value) }?.toMap()
    }

    fun formattedGeneral(): Map<String, String>? {
        return general?.entrySet()?.map { Pair(it.key, it.value.toString()) }?.toMap()
    }

    fun formattedPayment(): Map<String, String>? {
        return payment?.entrySet()?.map { Pair(it.key, it.value.toString()) }?.toMap()
    }

    fun formattedRegistration(): Map<String, String>? {
        return registration?.entrySet()?.map { Pair(it.key, it.value.toString()) }?.toMap()
    }
}


class Img(
        val url: String,
        val name: String
)