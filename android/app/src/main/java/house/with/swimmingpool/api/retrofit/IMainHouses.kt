package house.with.swimmingpool.api.retrofit

import house.with.swimmingpool.models.Videos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IMainHouses {
    @GET
    fun getMainHouses(
        @Url url: String = "https://private-99684c-housewithpool2.apiary-mock.com/stock-houses"
    ): Call<Videos>
}