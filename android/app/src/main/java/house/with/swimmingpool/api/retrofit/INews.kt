package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.App
import house.with.swimmingpool.models.NewsX
import house.with.swimmingpool.models.SingleNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface INews {

    @GET("news")
    fun getNews(
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<NewsX>

    @GET("news/{id}")
    fun getSingleNews(
            @Path("id") id: Int,
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<SingleNews>
}