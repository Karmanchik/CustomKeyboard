package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.AuthLogin
import house.with.swimmingpool.models.AuthRegisterFirst
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
}