package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.HouseCatalog
import house.with.swimmingpool.models.HouseExample
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface IRealty {

    @GET("objects")
    fun getHouses(): Call<HouseCatalog>

    @GET("objects/921")
    fun getHousesExample(): Call<HouseExample>
}