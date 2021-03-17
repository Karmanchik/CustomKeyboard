package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class MainBanners(
        val apiVersion: String?,
        @SerializedName("data") val data: List<MainBannersData>?,
        val error: Int?,
        val status: String?,
        val timestamp: Int?
)