package house.with.swimmingpool.api.retrofit

import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.models.*
import retrofit2.Call
import retrofit2.http.*

interface IAuthLogin {

    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(
        @Header("api-key") apikey: String,
        @Field("login") phone: String,
        @Field("password") code: String
    ): Call<AuthLogin>

    @FormUrlEncoded
    @POST("auth/phone")
    fun checkPhoneNumber(
        @Field("phone") phone: String,
        @Header("api-key") apikey: String = APIKEY,
        @Field("context") type: String = "client"
    ): Call<Answer<Int>>

    @FormUrlEncoded
    @POST("auth/registration")
    fun registerUser(
        @Header("api-key") apikey: String,
        @Field("phone") phone: String,
        @Field("context") type: String = "client"
    ): Call<Answer<AuthRegisterFirstData>>

    @FormUrlEncoded
    @POST("auth/confirm")
    fun confirmBySmsCode(
        @Field("phone") phone: String,
        @Field("code") code: String,
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("api-key") apikey: String = APIKEY
    ): Call<AuthRegisterSecond>

    @FormUrlEncoded
    @POST("auth/reset")
    fun getSmsCodeAgain(
        @Field("phone") phone: String,
        @Header("api-key") apikey: String = APIKEY
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

    @GET("settings")
    fun getSettings(
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<JsonObject>>

    @GET("settings/phone")
    fun getSettingsPhone(
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<String>>

    @FormUrlEncoded
    @POST("auth/reset")
    fun resetPassword(
        @Field("phone") phone: String,
        @Header("api-key") apikey: String = APIKEY,
        @Field("context") type: String = "client"
    ): Call<Answer<AuthRegisterFirstData>>
}