package house.with.swimmingpool.api.config.controllers

import house.with.swimmingpool.api.config.interfaces.IBannersService
import house.with.swimmingpool.api.retrofit.IBanners
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.Banners
import house.with.swimmingpool.models.BannersData
import house.with.swimmingpool.models.MainBanners
import house.with.swimmingpool.models.MainBannersData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class BannersServiceImpl : IBannersService {

    override fun getMainBanners(onLoaded: (data: List<MainBannersData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(IBanners::class.java)
            .getMainBanners()
            .enqueue(object : Callback<MainBanners> {
                override fun onResponse(call: Call<MainBanners>, response: Response<MainBanners>) {
                    try {
                        onLoaded.invoke(response.body()?.data, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<MainBanners>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    override fun getBanners(onLoaded: (data: List<BannersData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(IBanners::class.java)
            .getBanners()
            .enqueue(object : Callback<Banners> {
                override fun onResponse(call: Call<Banners>, response: Response<Banners>) {
                    try {
                        onLoaded.invoke(response.body()?.data, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<Banners>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    override suspend fun loadHeader(): Pair<List<MainBannersData>?, Throwable?> {
        return try {
            Pair(getRetrofit().create<IBanners>().getMainBanners().execute().body()!!.data, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    override suspend fun loadBanners(): Pair<List<BannersData>?, Throwable?> {
        return try {
            Pair(getRetrofit().create<IBanners>().getBanners().execute().body()!!.data, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

}