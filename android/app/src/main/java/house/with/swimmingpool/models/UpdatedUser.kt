package house.with.swimmingpool.models

data class UpdatedUser(
    val apiVersion: String,
    val `data`: User,
    val error: Int?,
    val status: String,
    val timestamp: Int
)