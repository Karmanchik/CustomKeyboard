package house.with.swimmingpool.models

import java.io.Serializable

data class StoriesItem(
    val message: String?,
    val poster: String?,
    var title: String?,
    val url: String?
): Serializable