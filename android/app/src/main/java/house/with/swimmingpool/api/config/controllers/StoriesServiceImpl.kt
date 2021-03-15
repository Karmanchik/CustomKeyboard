package house.with.swimmingpool.api.config.controllers

import android.util.Log
import house.with.swimmingpool.api.config.interfaces.IStoriesService
import house.with.swimmingpool.api.retrofit.IStories
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.Stories
import house.with.swimmingpool.models.StoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesServiceImpl : IStoriesService {
    override fun getStories(onLoaded: (data: List<StoriesData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(IStories :: class.java)
            .getStories()
            .enqueue(object : Callback<Stories> {
                override fun onResponse(call: Call<Stories>, response: Response<Stories>) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Stories>, t: Throwable) {
                    onLoaded.invoke(null, t)
                }
            })
    }
}