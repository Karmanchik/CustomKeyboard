package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

class ShortCollection(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val link: String? = null,
    var note: String? = null,
    var total: String? = null,
    val photos: List<String>? = null,
    @SerializedName("objects_list") val objects: List<HouseCatalogData>? = null
)