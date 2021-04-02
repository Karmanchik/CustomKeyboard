package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.AuthLogin
import house.with.swimmingpool.models.AuthRegisterFirst
import house.with.swimmingpool.models.AuthRegisterSecond
import house.with.swimmingpool.models.Code
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
            @Field("phone") phone: String
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
}