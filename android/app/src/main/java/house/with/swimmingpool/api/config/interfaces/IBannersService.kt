package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.BannersData
import house.with.swimmingpool.models.MainBannersData
import house.with.swimmingpool.models.StoriesData

interface IBannersService {
    fun getMainBanners(onLoaded: (data: List<MainBannersData>?, e: Throwable?) -> Unit)
    fun getBanners(onLoaded: (data: List<BannersData>?, e: Throwable?) -> Unit)

    suspend fun loadHeader(): Pair<List<MainBannersData>?, Throwable?>
    suspend fun loadBanners(): Pair<List<BannersData>?, Throwable?>
}