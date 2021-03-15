package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class NewsX(
    val apiVersion: String?,
    @SerializedName("data") val data: List<NewsData>?,
    val error: Any?,
    val status: String?,
    val timestamp: Int?
)