package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.config.interfaces.IStoriesService
import house.with.swimmingpool.api.retrofit.IStories
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.Stories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesServiceImpl : IStoriesService {
    override fun getStories() {
        getRetrofit().create(IStories :: class.java)
            .getStories()
            .enqueue(object : Callback<Stories> {
                override fun onResponse(call: Call<Stories>, response: Response<Stories>) {
                    Log.e("Done", "response.body()!!.apiVersion")
                }

                override fun onFailure(call: Call<Stories>, t: Throwable) {
                    Log.e("Done", "Error", t)
                }
            })
    }
}