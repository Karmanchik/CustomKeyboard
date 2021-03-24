package house.with.swimmingpool.models

class Answer<T>(
    val apiVersion: String,
    val timestamp: Long,
    val status: String?,
    val error: Int?,
    val data: T?
)