package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class Banners(
        val apiVersion: String?,
        @SerializedName("data") val data : List<BannersData>,
        val error: Int?,
        val status: String?,
        val timestamp: Int?
)