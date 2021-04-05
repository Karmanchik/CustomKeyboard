package house.with.swimmingpool.api.config.controllers

import android.util.Log
import com.google.gson.Gson
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

    override fun getHouseExample(
        id: Int,
        onLoaded: (data: HouseExampleData?, e: Throwable?) -> Unit
    ) {
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

    override fun getMyFavourites(onLoaded: (data: ListAnswerData<HouseCatalogData>?, e: Throwable?) -> Unit) {
        IRealty.api
            .getMyFavourites()
            .enqueue(object : Callback<ListAnswer<HouseCatalogData>> {
                override fun onResponse(
                    call: Call<ListAnswer<HouseCatalogData>>,
                    response: Response<ListAnswer<HouseCatalogData>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<ListAnswer<HouseCatalogData>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }

            })
    }

    override fun addToFavourites(id: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit) {
        IRealty.api
            .addToFavourites(id = id)
            .enqueue(object : Callback<Answer<Stub>> {
                override fun onResponse(
                    call: Call<Answer<Stub>>,
                    response: Response<Answer<Stub>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<Stub>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun removeFromFavourites(id: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit) {
        IRealty.api
            .removeFromFavourites(id = id)
            .enqueue(object : Callback<Answer<Stub>> {
                override fun onResponse(
                    call: Call<Answer<Stub>>,
                    response: Response<Answer<Stub>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<Stub>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun getSearches(onLoaded: (data: List<Search>?, e: Throwable?) -> Unit) {
        IRealty.api
            .getSearches()
            .enqueue(object : Callback<Answer<List<Search>>> {
                override fun onResponse(
                    call: Call<Answer<List<Search>>>,
                    response: Response<Answer<List<Search>>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<List<Search>>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun getSearch(id: String, onLoaded: (data: Search?, e: Throwable?) -> Unit) {
        IRealty.api
            .getSearch(id = id)
            .enqueue(object : Callback<Answer<Search>> {
                override fun onResponse(
                    call: Call<Answer<Search>>,
                    response: Response<Answer<Search>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<Search>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun deleteSearch(id: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit) {
        IRealty.api
            .deleteSearch(id = id)
            .enqueue(object : Callback<Answer<Stub>> {
                override fun onResponse(
                    call: Call<Answer<Stub>>,
                    response: Response<Answer<Stub>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<Stub>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun addSearch(
        name: String,
        filter: FilterObjectsRequest,
        onLoaded: (data: Stub?, e: Throwable?) -> Unit
    ) {
        IRealty.api
            .addSearch(name = name, filter = Gson().toJson(filter))
            .enqueue(object : Callback<Answer<Stub>> {
                override fun onResponse(
                    call: Call<Answer<Stub>>,
                    response: Response<Answer<Stub>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<Stub>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun updateSearch(
        id: String,
        name: String,
        push: Boolean,
        filter: FilterObjectsRequest,
        onLoaded: (data: Stub?, e: Throwable?) -> Unit
    ) {
        IRealty.api
            .updateSearch(id = id, name = name, filter = Gson().toJson(filter))
            .enqueue(object : Callback<Answer<Stub>> {
                override fun onResponse(
                    call: Call<Answer<Stub>>,
                    response: Response<Answer<Stub>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<Stub>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun getCollections(onLoaded: (data: List<ShortCollection>?, e: Throwable?) -> Unit) {
        IRealty.api
            .getCollections()
            .enqueue(object : Callback<Answer<List<ShortCollection>>> {
                override fun onResponse(
                    call: Call<Answer<List<ShortCollection>>>,
                    response: Response<Answer<List<ShortCollection>>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<List<ShortCollection>>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    override fun getCollection(
        id: String,
        onLoaded: (data: ShortCollection?, e: Throwable?) -> Unit
    ) {
        IRealty.api
            .getCollection(id = id)
            .enqueue(object : Callback<Answer<ShortCollection>> {
                override fun onResponse(
                    call: Call<Answer<ShortCollection>>,
                    response: Response<Answer<ShortCollection>>
                ) {
                    onLoaded.invoke(response.body()?.data, null)
                }

                override fun onFailure(call: Call<Answer<ShortCollection>>, t: Throwable) {
                    onLoaded.invoke(null, t)
                    Log.e("taskException", "error", t)
                }
            })
    }

    fun getParams(onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit) {
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
}