package house.with.swimmingpool.models

data class Params(
    val apiVersion: String,
    val `data`: Data,
    val error: Any,
    val status: String,
    val timestamp: Int
)