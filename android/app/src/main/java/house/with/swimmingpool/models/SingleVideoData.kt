package house.with.swimmingpool.models

data class SingleVideoData(
    val content: String?,
    val date: String?,
    val hits: Int?,
    val icon: String?,
    val id: Int?,
    val introtext: String?,
    val photos: List<String>?,
    val tags: List<Any>?,
    val title: String?,
    val video: String?,
    val linked_objects: List<HouseCatalogData>?
)