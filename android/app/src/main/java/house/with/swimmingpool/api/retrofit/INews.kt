package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.NewsX
import house.with.swimmingpool.models.Stories
import retrofit2.Call
import retrofit2.http.GET

interface INews {

    @GET("stories")
    fun getNews(): Call<NewsX>
}