package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.App
import house.with.swimmingpool.models.UpdatedUser
import house.with.swimmingpool.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface IUpdateUser {

    @PUT("user")
    fun updateUserInfo(
            @Body request: User,
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<UpdatedUser>
}