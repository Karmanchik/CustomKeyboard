package house.with.swimmingpool.models

import com.google.gson.annotations.SerializedName

data class BannersData(
        @SerializedName("200x200") val smallBanner: String?,
        @SerializedName("350x200") val bigBanner: String?,
        val id: String?,
        val link: String?,
        val name: String?
)