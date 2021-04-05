package house.with.swimmingpool.models

data class UpdatedUser(
    val apiVersion: String,
    val `data`: User,
    val error: Any,
    val status: String,
    val timestamp: Int
)