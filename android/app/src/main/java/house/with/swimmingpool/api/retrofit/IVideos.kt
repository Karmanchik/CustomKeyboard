package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.Videos
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface IVideos {

    @GET
    fun getVideos(
        @Url url: String = "https://private-99684c-housewithpool2.apiary-mock.com/videos"
    ): Call<Videos>

}