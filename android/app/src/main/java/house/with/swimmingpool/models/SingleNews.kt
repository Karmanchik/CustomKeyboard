package house.with.swimmingpool.models

data class SingleNews(
        val apiVersion: String?,
        val `data`: SingleNewsData?,
        val error: Int?,
        val status: String?,
        val timestamp: Int?
)