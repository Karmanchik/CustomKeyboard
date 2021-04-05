package house.with.swimmingpool.api.config.interfaces

import house.with.swimmingpool.models.NewsData

interface INewsService {
    fun getNews(onLoaded: (data: List<NewsData>?, e: Throwable?) -> Unit)
    suspend fun loadNews(): Pair<List<NewsData>?, Throwable?>
}