package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class HouseCatalog(
    val apiVersion: String?,
    @SerializedName("data") val houseCatalogData: List<HouseCatalogData>?,
    val error: Int?,
    val status: String?,
    val timestamp: Int?
)