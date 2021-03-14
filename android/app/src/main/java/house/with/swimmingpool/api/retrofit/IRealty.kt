package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.HouseCatalog
import house.with.swimmingpool.models.Stories
import retrofit2.Call
import retrofit2.http.GET

interface IRealty {

    @GET("objects")
    fun getHouses(): Call<HouseCatalog>
}