package house.with.swimmingpool.models

import house.with.swimmingpool.models.request.FilterObjectsRequest

class Search(
    val id: Int? = null,
    val name: String? = null,
    val config: FilterObjectsRequest? = null,
    val createdon: String? = null,
    var push: Boolean? = null
)