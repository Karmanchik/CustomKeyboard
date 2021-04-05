package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.App
import house.with.swimmingpool.models.*
import retrofit2.Call
import retrofit2.http.*

interface IAuthLogin {

    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(
            @Header("api-key") apikey: String,
            @Field("phone") phone: String,
            @Field("code") code: String
    ): Call<AuthLogin>

    @FormUrlEncoded
    @POST("auth/first")
    fun registerUser(
            @Header("api-key") apikey: String,
            @Field("phone") phone: String,
            @Field("context") type: String = "client"
    ): Call<AuthRegisterFirst>

    @FormUrlEncoded
    @POST("auth/second")
    fun confirmBySmsCode(
            @Header("api-key") apikey: String,
            @Field("phone") phone: String,
            @Field("code") code: String
    ): Call<AuthRegisterSecond>

    @FormUrlEncoded
    @POST("auth/code")
    fun getSmsCodeAgain(
            @Header("api-key") apikey: String,
            @Field("phone") phone: String
    ): Call<Code>

    @FormUrlEncoded
    @POST("auth/password")
    fun setPassword(
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b",
            @Field("new_password") newPassword: String,
            @Field("old_password") oldPassword: String
    ): Call<UpdatedUser>
}