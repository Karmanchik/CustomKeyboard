package house.with.swimmingpool.helpers

import androidx.room.Database
import androidx.room.RoomDatabase
import house.with.swimmingpool.models.HouseCatalogData

@Database(entities = [HouseCatalogData::class], version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun eventsDao(): HouseDao?
}