package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class HouseExample(
    val apiVersion: String?,
    @SerializedName("data") val data: HouseExampleData?,
    val error: Int?,
    val status: String?,
    val timestamp: Int?
)