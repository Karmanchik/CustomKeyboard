package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.App
import house.with.swimmingpool.models.Answer
import house.with.swimmingpool.models.UpdatedUser
import house.with.swimmingpool.models.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface IUpdateUser {

    @PUT("user")
    fun updateUserInfo(
            @Body request: User,
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<UpdatedUser>

    @Multipart
    @POST("file")
    fun updateAvatar(
            @Part file: MultipartBody.Part?,
//            @Part("Content-Type") type: RequestBody?,
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ):Call<Answer<User>>

    @GET("user")
    fun getUserInfo(
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<UpdatedUser>
}