package house.with.swimmingpool.api.config.controllers

import android.util.Log
import com.google.gson.JsonObject
import house.with.swimmingpool.api.config.interfaces.IRealtyService
import house.with.swimmingpool.api.retrofit.IRealty
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RealtyServiceImpl : IRealtyService {

    override fun getHouseCatalog(onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit) {
        getRetrofit().create(IRealty::class.java)
            .getHouses()
            .enqueue(object : Callback<HouseCatalog> {
                override fun onResponse(
                    call: Call<HouseCatalog>,
                    response: Response<HouseCatalog>
                ) {
                    try {
                        onLoaded.invoke(response.body()?.houseCatalogData, null)
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<HouseCatalog>, t: Throwable) {
                    try {
                        onLoaded.invoke(null, t)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    override fun getHouseExample(id: Int, onLoaded: (data: HouseExampleData?, e: Throwable?) -> Unit) {
        getRetrofit().create(IRealty::class.java)
            .getHousesExample(id)
            .enqueue(object : Callback<HouseExample> {
                override fun onResponse(
                    call: Call<HouseExample>,
                    response: Response<HouseExample>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<HouseExample>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun getParamsForFilter(): Answer<JsonObject>? =
        getRetrofit().create(IRealty::class.java).getParamsForFilter().execute().body()
}