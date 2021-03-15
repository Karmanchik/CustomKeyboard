package house.with.swimmingpool

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import house.with.swimmingpool.api.config.controllers.NewsServiceImpl
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.api.config.controllers.StoriesServiceImpl
import house.with.swimmingpool.api.config.controllers.VideosServiceImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            true
        }
        navView.setupWithNavController(navController)
    }
}