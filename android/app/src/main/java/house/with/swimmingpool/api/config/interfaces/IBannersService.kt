package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.BannersData
import house.with.swimmingpool.models.MainBannersData

interface IBannersService {
    fun getMainBanners(onLoaded: (data: List<MainBannersData>?, e: Throwable?) -> Unit)

    fun getBanners(onLoaded: (data: List<BannersData>?, e: Throwable?) -> Unit)
}