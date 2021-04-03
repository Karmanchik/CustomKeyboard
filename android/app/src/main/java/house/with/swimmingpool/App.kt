package house.with.swimmingpool

import android.app.Application
import android.hardware.SensorDirectChannel
import androidx.room.Room
import house.with.swimmingpool.helpers.RoomDataBase
import house.with.swimmingpool.helpers.Setting

class App: Application() {

    companion object {
        lateinit var setting: Setting
        var database: RoomDataBase? = null
        const val SEARCH_ACTIVITY = "SEARCH_ACTIVITY"
        const val SEARCH_ACTIVITY_TO_CATALOG = "SEARCH_ACTIVITY_TO_CATALOG"
        const val SEARCH_ACTIVITY_TO_OBJECT = "SEARCH_ACTIVITY_TO_OBJECT"
        const val HOUSE_WITH_SWIMMING_POOL = "HOUSE_WITH_SWIMMING_POOL"
        const val SEND_REQUEST_CONSULTATION = "SEND_REQUEST_CONSULTATION"
        const val TYPE_OF_POPUP = "TYPE_OF_POPUP"
    }

    override fun onCreate() {
        super.onCreate()
        setting = Setting(applicationContext)
        database = Room.databaseBuilder(this, RoomDataBase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

}