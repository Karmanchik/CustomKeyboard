package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.VideosData

interface IVideosService {
    fun getVideos(onLoaded: (data: List<VideosData>?, e: Throwable?) -> Unit)
    suspend fun loadVideos(): Pair<List<VideosData>?, Throwable?>
}