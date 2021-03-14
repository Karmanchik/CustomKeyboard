package house.with.swimmingpool.models

import com.google.gson.JsonArray

data class HouseExampleData(
    val analogs: List<HouseCatalogData>?,
    val children: List<Children>?,
    val communications: Any?, //JsonArray?,
    val description: String?,
    val discount: Int?,
    val editedon: String?,
    val galleries: Any?, //JsonArray,
    val general: Any?, //JsonArray,
    val geolocation: Geolocation?,
    val hits: Int?,
    val icon: String?,
    val id: Int?,
    val isFavourite: Boolean?,
    val location: String?,
    val mainTags: List<String>?,
    val note: String?,
    val payment: Any?, //JsonArray?,
    val price: String?,
    val price_of_one_meter: String?,
    val promo: String,
    val registration: Any?, //JsonArray?,
    val statuses: List<String>?,
    val title: String?,
    val type: String?,
    val video: List<String?>
)