package house.with.swimmingpool.api.config.controllers

import house.with.swimmingpool.api.config.interfaces.INewsService
import house.with.swimmingpool.api.retrofit.INews
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.NewsData
import house.with.swimmingpool.models.NewsX
import house.with.swimmingpool.models.SingleNews
import house.with.swimmingpool.models.SingleNewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class NewsServiceImpl : INewsService {

    override fun getNews(onLoaded: (data: List<NewsData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(INews::class.java)
            .getNews()
            .enqueue(object : Callback<NewsX> {
                override fun onResponse(call: Call<NewsX>, response: Response<NewsX>) {
                    try {
                        onLoaded.invoke(response.body()?.data, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<NewsX>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    override suspend fun loadNews(): Pair<List<NewsData>?, Throwable?> {
        return try {
            Pair(getRetrofit().create<INews>().getNews().execute().body()!!.data, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    fun getSingleNews(id: Int, onLoaded: (data: SingleNewsData?, e: Throwable?) -> Unit) {
        getRetrofit().create(INews::class.java)
            .getSingleNews(id)
            .enqueue(object : Callback<SingleNews> {
                override fun onResponse(call: Call<SingleNews>, response: Response<SingleNews>) {
                    try {
                        onLoaded.invoke(response.body()?.data, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<SingleNews>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

}