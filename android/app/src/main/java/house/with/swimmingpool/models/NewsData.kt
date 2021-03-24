package house.with.swimmingpool.models

import java.io.Serializable

data class NewsData(
    val date: String?,
    val hits: Int?,
    val icon: String?,
    val id: Int?,
    val introtext: String?,
    val tags: List<String>?,
    val title: String?
): Serializable {

    companion object {
        fun createEmpty(): NewsData = NewsData(
                "", 0, null, 0, "wefw", null, null
        )
    }

}