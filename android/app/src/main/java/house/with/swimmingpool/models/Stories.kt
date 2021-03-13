package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class Stories(
    val apiVersion: String,
    @SerializedName("data") val data: List<StoriesData>,
    val error: Int?,
    val status: String,
    val timestamp: Int
)