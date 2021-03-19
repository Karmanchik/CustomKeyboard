package house.with.swimmingpool.models

import java.io.Serializable

data class StoriesData(
    val icon: String?,
    val items: List<StoriesItem>?,
    val title: String?
): Serializable