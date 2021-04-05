package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.StoriesData

interface IStoriesService {
    fun getStories(onLoaded: (data: List<StoriesData>?, e: Throwable?) -> Unit)
    suspend fun loadStories(): Pair<List<StoriesData>?, Throwable?>
}