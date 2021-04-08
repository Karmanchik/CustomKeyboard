package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.*

interface IAuthService {
    fun loginUser(phone: String, code: String, onLoaded: (data: AuthLoginData?, e: Throwable?)-> Unit)

    fun registerUserFirst(phone: String, onLoaded: (data: AuthRegisterFirstData?, e: Throwable?) -> Unit)

    fun confirmBySmsCode(phone: String, code: String, onLoaded: (data: AuthRegisterSecondData?, e: Throwable?) -> Unit)

    fun getSmsCodeAgain(phone: String, onLoaded: (data: CodeData?, e: Throwable?) -> Unit)

    fun setPassword(newPassword: String, oldPassword: String, onLoaded: (data: UpdatedUser?, e: Throwable?) -> Unit)
}