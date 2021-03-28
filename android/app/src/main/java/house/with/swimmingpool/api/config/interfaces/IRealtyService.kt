package house.with.swimmingpool.api.config.interfaces

import com.google.gson.JsonObject
import house.with.swimmingpool.models.Answer
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.HouseExampleData
import house.with.swimmingpool.models.request.FilterObjectsRequest

interface IRealtyService {

    fun getHouseCatalog(onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit)
    fun getHouseExample(id: Int, onLoaded: (data: HouseExampleData?, e: Throwable?) -> Unit)
    fun getParamsForFilter(): Answer<JsonObject>?
    fun getObjectsByFilter(filter: FilterObjectsRequest, onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit)

}