package house.with.swimmingpool.models

data class PushInfo(
    var action: String?,
    var description: String?,
    var photo: String?,
    var time: Int?,
    var title: String?
)

class AnswerPush(
    val apiVersion: String,
    val timestamp: Long,
    val status: String?,
    val error: Int?,
    val data: PushInfo?
)