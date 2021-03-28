package house.with.swimmingpool.api.retrofit

import com.google.gson.JsonObject
import house.with.swimmingpool.models.Answer
import house.with.swimmingpool.models.CountList
import house.with.swimmingpool.models.HouseCatalog
import house.with.swimmingpool.models.HouseExample
import house.with.swimmingpool.models.request.FilterObjectsRequest
import retrofit2.Call
import retrofit2.create
import retrofit2.http.*

interface IRealty {

    @GET("objects")
    fun getHouses(): Call<HouseCatalog>

    @GET("objects/{id}")
    fun getHousesExample(
            @Path("id") id: Int
    ): Call<HouseExample>

    @GET("params")
    fun getParamsForFilter(
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<JsonObject>>

    @POST("objects")
    fun getObjectsWithFilter(
        @Body request: FilterObjectsRequest
    ): Call<Answer<CountList>>

    companion object {
        val api = mainRetrofit.create<IRealty>()
    }

}