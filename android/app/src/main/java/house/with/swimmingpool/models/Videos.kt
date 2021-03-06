package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class Videos(
    val apiVersion: String,
    @SerializedName("data") val data: List<VideosData>,
    val error: Any,
    val status: String,
    val timestamp: Int
)