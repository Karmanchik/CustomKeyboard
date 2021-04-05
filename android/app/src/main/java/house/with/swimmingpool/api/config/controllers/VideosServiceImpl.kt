package house.with.swimmingpool.api.config.controllers

import house.with.swimmingpool.api.config.interfaces.IVideosService
import house.with.swimmingpool.api.retrofit.IVideos
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.Videos
import house.with.swimmingpool.models.VideosData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class VideosServiceImpl : IVideosService {

    override fun getVideos(onLoaded: (data: List<VideosData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(IVideos::class.java)
            .getVideos()
            .enqueue(object : Callback<Videos> {
                override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                    try {
                        onLoaded.invoke(response.body()?.data, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<Videos>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    override suspend fun loadVideos(): Pair<List<VideosData>?, Throwable?> {
        return try {
            Pair(getRetrofit().create<IVideos>().getVideos().execute().body()!!.data, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

}