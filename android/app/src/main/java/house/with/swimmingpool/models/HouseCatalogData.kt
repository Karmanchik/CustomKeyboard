package house.with.swimmingpool.models

data class HouseCatalogData(
    val description: String?,
    val discount: Int?,
    val editedon: String?,
    val geolocation: Geolocation?,
    val hits: Int?,
    val icon: String?,
    val id: Int?,
    val isFavourite: Boolean?,
    val location: String?,
    val mainTags: List<MainTags>?,
    val note: Any?,//String?, //fix me!!!
    val photos: List<String>?,
    val price: String?,
    val price_of_one_meter: String?,
    val promo: String?,
    val square: Any?,
    val square_area: Any?,
    val statuses: List<String>?,
    val title: String?,
    val type: String?,
    val video: List<Any>?
)