package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.HouseCatalogData

interface IRealtyService {

    fun getHouseCatalog(onLoaded: (data: List<HouseCatalogData>?, e: Throwable?) -> Unit)
    fun getHouseExample()

}