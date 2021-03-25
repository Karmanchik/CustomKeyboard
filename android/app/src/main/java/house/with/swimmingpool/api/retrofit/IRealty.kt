package house.with.swimmingpool.api.retrofit

import com.google.gson.JsonObject
import house.with.swimmingpool.models.Answer
import house.with.swimmingpool.models.HouseCatalog
import house.with.swimmingpool.models.HouseExample
import retrofit2.Call
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

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

    companion object {
        val api = mainRetrofit.create<IRealty>()
    }

}