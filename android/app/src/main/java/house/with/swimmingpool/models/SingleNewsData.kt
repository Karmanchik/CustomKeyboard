package house.with.swimmingpool.models

data class SingleNewsData(
    val content: String?,
    val date: String?,
    val hits: Int?,
    val icon: String?,
    val id: Int?,
    val introtext: String?,
    val linked_objects: List<HouseCatalogData>?,
    val photos: List<String>?,
    val tags: List<String>?,
    val title: String?,
    val video: List<String>?
)