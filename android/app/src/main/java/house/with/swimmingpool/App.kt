package house.with.swimmingpool

import android.app.Application
import androidx.room.Room
import house.with.swimmingpool.helpers.RoomDataBase
import house.with.swimmingpool.helpers.Setting

class App: Application() {

    companion object {
        lateinit var setting: Setting
        var database: RoomDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        setting = Setting(applicationContext)
        database = Room.databaseBuilder(this, RoomDataBase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

}