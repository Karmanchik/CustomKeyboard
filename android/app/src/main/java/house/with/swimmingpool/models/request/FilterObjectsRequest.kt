package house.with.swimmingpool.models.request

data class FilterObjectsRequest(
    var advantages: List<String>? = null,
    var buildingClass: List<String>? = null,
    var completed: Boolean? = null,
    var dir: String? = null,
    var districts: List<String>? = null,
    var extend: Boolean? = null,
    var interiorTypes: List<String>? = null,
    var limit: Int = 1000,
    var microDistricts: List<String>? = null,
    var new: Boolean? = null,
    var paymentTypes: List<String>? = null,
    var popular: Boolean? = null,
    var price_all_from: String? = null,
    var price_all_to: String? = null,
    var recommended: Boolean? = null,
    var registrationTypes: List<Any>? = null,
    var seaDistance: Int? = null,
    var sort: String? = null,
    var square_all_from: String? = null,
    var square_all_to: String? = null,
    var start: Int = 0,
    var search: String? = null,
    var types: List<String>? = null
)