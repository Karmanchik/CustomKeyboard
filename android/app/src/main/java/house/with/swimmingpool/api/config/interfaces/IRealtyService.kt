package house.with.swimmingpool.api.config.interfaces

import com.google.gson.JsonObject
import house.with.swimmingpool.models.Answer
import house.with.swimmingpool.models.HouseCatalogData

interface IRealtyService {

    fun getHouseCatalog(onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit)
    fun getHouseExample()
    fun getParamsForFilter(): Answer<JsonObject>?

}