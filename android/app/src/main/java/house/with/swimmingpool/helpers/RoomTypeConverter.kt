package house.with.swimmingpool.helpers

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import house.with.swimmingpool.models.MainTags

class RoomTypeConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStrings(list: List<String>?): String? {
            return list?.joinToString(":::")
        }

        @TypeConverter
        @JvmStatic
        fun toStrings(data: String?): List<String>? {
            return data?.split(":::")
        }

        @TypeConverter
        @JvmStatic
        fun fromMainTags(list: List<MainTags>?): String? {
            return list?.joinToString(":::") { Gson().toJson(it) }
        }

        @TypeConverter
        @JvmStatic
        fun toMainTags(data: String?): List<MainTags>? {
            return data?.split(":::")?.map { Gson().fromJson(it, MainTags::class.java) }
        }

        @TypeConverter
        @JvmStatic
        fun fromJson(list: JsonElement?): String? {
            return list?.let { Gson().toJson(it) }
        }

        @TypeConverter
        @JvmStatic
        fun toJson(data: String?): JsonElement? {
            return data?.let { Gson().fromJson(it, JsonElement::class.java) }
        }

        @TypeConverter
        @JvmStatic
        fun fromJsonArray(list: JsonArray?): String? {
            return list?.let { Gson().toJson(it) }
        }

        @TypeConverter
        @JvmStatic
        fun toJsonArray(data: String?): JsonArray? {
            return data?.let { Gson().fromJson(it, JsonArray::class.java) }
        }
    }

}
