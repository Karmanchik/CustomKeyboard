package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.config.interfaces.IVideosService
import house.with.swimmingpool.api.retrofit.IVideos
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.Videos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosServiceImpl: IVideosService {

    override fun getVideos(){
        getRetrofit().create(IVideos :: class.java)
            .getVideos()
            .enqueue(object : Callback<Videos> {
                override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                    Log.e("Done", "response.body()!!.data!!.formattedRegistration().toString()")
                }

                override fun onFailure(call: Call<Videos>, t: Throwable) {
                    Log.e("Done", "Error", t)
                }
            })
    }

}