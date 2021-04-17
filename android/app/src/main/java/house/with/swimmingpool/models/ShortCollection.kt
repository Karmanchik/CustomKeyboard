package house.with.swimmingpool.models

class ShortCollection(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val link: String? = null,
    var note: String? = null,
    var total: String? = null,
    val photos: List<String>? = null,
    val objects: List<HouseCatalogData>? = null
)