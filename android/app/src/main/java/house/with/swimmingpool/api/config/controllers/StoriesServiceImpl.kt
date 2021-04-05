package house.with.swimmingpool.api.config.controllers

import house.with.swimmingpool.api.config.interfaces.IStoriesService
import house.with.swimmingpool.api.retrofit.IStories
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.Stories
import house.with.swimmingpool.models.StoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class StoriesServiceImpl : IStoriesService {
    override fun getStories(onLoaded: (data: List<StoriesData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(IStories::class.java)
            .getStories()
            .enqueue(object : Callback<Stories> {
                override fun onResponse(call: Call<Stories>, response: Response<Stories>) {
                    try {
                        onLoaded.invoke(response.body()?.data, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<Stories>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    override suspend fun loadStories(): Pair<List<StoriesData>?, Throwable?> {
        return try {
            Pair(getRetrofit().create<IStories>().getStories().execute().body()!!.data, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }
}