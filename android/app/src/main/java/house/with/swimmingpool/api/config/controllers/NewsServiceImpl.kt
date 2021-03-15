package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.config.interfaces.INewsService
import house.with.swimmingpool.api.retrofit.INews
import house.with.swimmingpool.api.retrofit.IVideos
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.NewsX
import retrofit2.Callback
import house.with.swimmingpool.models.Videos
import retrofit2.Call
import retrofit2.Response

class NewsServiceImpl: INewsService {

    override fun getNews() {
        getRetrofit().create(INews::class.java)
            .getNews()
            .enqueue(object : Callback<NewsX> {
                override fun onResponse(call: Call<NewsX>, response: Response<NewsX>) {
                    Log.e("Done", "response.body()!!.data!!.formattedRegistration().toString()")
                }

                override fun onFailure(call: Call<NewsX>, t: Throwable) {
                    Log.e("Done", "Error", t)
                }
            })
    }

}