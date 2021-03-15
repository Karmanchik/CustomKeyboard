package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.Videos
import retrofit2.Call
import retrofit2.http.GET


interface IVideos {

    @GET("videos")
    fun getVideos(): Call<Videos>
}