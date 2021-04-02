package house.with.swimmingpool.models

data class AuthRegisterFirst(
        val apiVersion: String,
        val `data`: AuthRegisterFirstData,
        val error: Any,
        val status: String,
        val timestamp: Int
)