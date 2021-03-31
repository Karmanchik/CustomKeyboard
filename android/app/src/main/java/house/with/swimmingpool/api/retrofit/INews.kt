package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.NewsX
import house.with.swimmingpool.models.SingleNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface INews {

    @GET("stories")
    fun getNews(): Call<NewsX>

    @GET("news/{id}")
    fun getSingleNews(
            @Path("id") id: Int
    ): Call<SingleNews>
}