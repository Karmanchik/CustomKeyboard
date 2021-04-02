package house.with.swimmingpool.models

data class AuthLogin(
        val apiVersion: String,
        val `data`: AuthLoginData,
        val error: Any,
        val status: String,
        val timestamp: Int
)