package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.Banners
import house.with.swimmingpool.models.MainBanners
import house.with.swimmingpool.models.NewsX
import retrofit2.Call
import retrofit2.http.GET

interface IBanners {

    @GET("mainbanners")
    fun getMainBanners(): Call<MainBanners>

    @GET("banners")
    fun getBanners(): Call<Banners>
}