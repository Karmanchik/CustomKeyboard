package house.with.swimmingpool.api.config.controllers

import house.with.swimmingpool.api.config.interfaces.IVideosService
import house.with.swimmingpool.api.retrofit.IVideos
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.Videos
import house.with.swimmingpool.models.VideosData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosServiceImpl: IVideosService {

    override fun getVideos(onLoaded: (data: List<VideosData>?, e: Throwable?) -> Unit){
        getRetrofit().create(IVideos :: class.java)
            .getVideos()
            .enqueue(object : Callback<Videos> {
                override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Videos>, t: Throwable) {
                    onLoaded.invoke(null, t)
                }
            })
    }

}