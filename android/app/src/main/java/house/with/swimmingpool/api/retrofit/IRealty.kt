package house.with.swimmingpool.api.retrofit

import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.models.*
import house.with.swimmingpool.models.request.FilterObjectsRequest
import retrofit2.Call
import retrofit2.create
import retrofit2.http.*

interface IRealty {

    @GET("objects")
    fun getHouses(
            @Header("Authorization") token: String? = App.setting.apiToken,
            @Header("phone") phone: String? = App.setting.phone,
            @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<HouseCatalog>

    @GET("objects/{id}")
    fun getHousesExample(
        @Path("id") id: Int,
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<HouseExample>

    @GET("params")
    fun getParamsForFilter(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<JsonObject>>

    @POST("objects")
    fun getObjectsWithFilter(
        @Body request: FilterObjectsRequest,
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<CountList>>


    // избранное

    @GET("fav")
    fun getMyFavourites(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<ListAnswer<HouseCatalogData>>

    @PUT("fav/{id}")
    fun addToFavourites(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: Int,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<AddRemoweFavRequest>

    @DELETE("fav/{id}")
    fun removeFromFavourites(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: Int,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<AddRemoweFavRequest>


    // фильтры

    @GET("searches")
    fun getSearches(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<List<Search>>>

    @GET("searches/{id}")
    fun getSearch(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: String,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<Search>>

    @DELETE("searches/{id}")
    fun deleteSearch(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: String,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<Stub>>

    @FormUrlEncoded
    @POST("searches")
    fun addSearch(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Field("name") name: String,
        @Field("config") filter: String?,
        @Field("push") push: Boolean = true,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<Stub>>

    @FormUrlEncoded
    @PUT("searches/{id}")
    fun updateSearch(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: String,
        @Field("name") name: String,
        @Field("config") filter: String?,
        @Field("push") push: Boolean = true,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<Stub>>


    // подборки

    @GET("collections")
    fun getCollections(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<List<ShortCollection>>>

    @GET("collections/{id}")
    fun getCollection(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: String,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<ShortCollection>>

    @FormUrlEncoded
    @PUT("collections/{id}")
    fun changeNoteForCollection(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b",
        @Path("id") id: String,
        @Field("note") note: String
    ): Call<Answer<Stub>>

    @DELETE("collections/{id}")
    fun deleteCollection(
        @Header("Authorization") token: String? = App.setting.apiToken,
        @Header("phone") phone: String? = App.setting.phone,
        @Path("id") id: String,
        @Header("api-key") key: String = "postman0ebba-60b1-40b4-b189-f409d5d1ad7b"
    ): Call<Answer<Stub>>

    @FormUrlEncoded
    @POST("mail")
    fun consultationRequest(
        @Field("email") email: String?,
        @Field("phone") phone: String?,
        @Field("message") message: String?,
        @Header("api-key") key: String = APIKEY
    ): Call<Answer<Any?>>

    companion object {
        val api = mainRetrofit.create<IRealty>()
    }

}