package house.with.swimmingpool.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import house.with.swimmingpool.helpers.RoomTypeConverter
import java.io.Serializable

@Entity(tableName = "house")
@TypeConverters(RoomTypeConverter::class)
data class HouseCatalogData(
    val description: String?,
    val discount: Int?,
    val editedon: String?,
    @Embedded
    val geolocation: Geolocation?,
    val hits: Int?,
    val icon: String?,
    @PrimaryKey
    val id: Int = 0,
    val isFavourite: Boolean?,
    val location: String?,
    val mainTags: List<MainTags>?,
    val note: JsonArray?,//String?, //fix me!!!
    val photos: List<String>?,
    val price: String?,
    val price_of_one_meter: String?,
    val promo: String?,
    val square: String?,
    val square_area: JsonElement?,
    val statuses: List<String>?,
    val title: String?,
    val type: String?,
    val video: List<String>?
): Serializable