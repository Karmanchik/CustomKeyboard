package house.with.swimmingpool.models

class ListAnswer<T>(
    val apiVersion: String,
    val timestamp: Long,
    val status: String?,
    val error: Int?,
    val data: ListAnswerData<T>?
)

class ListAnswerData<T>(
    val total: Int? = null,
    val list: List<T>? = null
)