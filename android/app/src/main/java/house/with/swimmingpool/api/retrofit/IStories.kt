package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.Stories
import retrofit2.Call
import retrofit2.http.*

interface IStories {

    @GET("/stories")
    fun getStories(): Call<Stories>

    @GET("/house/id")
    fun getHouse(): Call<Stories>
}