package house.with.swimmingpool.api.config.controllers

import android.util.Log
import com.google.gson.JsonObject
import house.with.swimmingpool.api.config.interfaces.IRealtyService
import house.with.swimmingpool.api.retrofit.IRealty
import house.with.swimmingpool.api.retrofit.getRetrofit
import house.with.swimmingpool.models.*
import house.with.swimmingpool.models.request.FilterObjectsRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

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
                    Log.e("taskException", "error1", t)
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

    override fun getObjectsByFilter(
        filter: FilterObjectsRequest,
        onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit
    ) {
        getRetrofit().create<IRealty>()
            .getObjectsWithFilter(filter)
            .enqueue(object : Callback<Answer<CountList>> {
                override fun onResponse(
                    call: Call<Answer<CountList>>,
                    response: Response<Answer<CountList>>
                ) {
                    onLoaded.invoke(response.body()?.data?.list, null)
                }

                override fun onFailure(call: Call<Answer<CountList>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }

            })
    }
}