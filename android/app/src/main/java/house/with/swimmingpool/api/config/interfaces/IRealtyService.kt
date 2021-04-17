package house.with.swimmingpool.api.config.interfaces

import com.google.gson.JsonObject
import house.with.swimmingpool.models.*
import house.with.swimmingpool.models.request.FilterObjectsRequest

interface IRealtyService {

    fun getHouseCatalog(onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit)
    fun getHouseExample(id: Int, onLoaded: (data: HouseExampleData?, e: Throwable?, error: Int?) -> Unit)
    fun getParamsForFilter(): Answer<JsonObject>?
    fun getObjectsByFilter(filter: FilterObjectsRequest, onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit)

    // избранное
    fun getMyFavourites(onLoaded: (data: ListAnswerData<HouseCatalogData>?, e: Throwable?) -> Unit)
    fun addToFavourites(id: Int, onLoaded: (status: String?, e: Throwable?) -> Unit)
    fun removeFromFavourites(id: Int, onLoaded: (status: String?, e: Throwable?) -> Unit)

    // фильтры
    fun getSearches(onLoaded: (data: List<Search>?, e: Throwable?) -> Unit)
    fun getSearch(id: String, onLoaded: (data: Search?, e: Throwable?) -> Unit)
    fun deleteSearch(id: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit)
    fun addSearch(name: String, filter: FilterObjectsRequest, onLoaded: (data: Stub?, e: Throwable?) -> Unit)
    fun updateSearch(id: String, name: String, push: Boolean, filter: FilterObjectsRequest, onLoaded: (data: Stub?, e: Throwable?) -> Unit)

    // подборки
    fun getCollections(onLoaded: (data: List<ShortCollection>?, e: Throwable?) -> Unit)
    fun getCollection(id: String, onLoaded: (data: ShortCollection?, e: Throwable?) -> Unit)
    fun changeNoteInCollection(id: String, note: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit)
    fun deleteCollection(id: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit)
    fun createCollection(name: String, note: String, onLoaded: (data: Stub?, e: Throwable?) -> Unit)

}