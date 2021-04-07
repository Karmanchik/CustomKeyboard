package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.App
import house.with.swimmingpool.models.Banners
import house.with.swimmingpool.models.MainBanners
import house.with.swimmingpool.models.NewsX
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface IBanners {

    @GET("mainbanners")
    fun getMainBanners(
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<MainBanners>

    @GET("banners")
    fun getBanners(
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Banners>
}