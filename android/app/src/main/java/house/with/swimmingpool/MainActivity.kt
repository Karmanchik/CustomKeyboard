package house.with.swimmingpool

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val fab = findViewById<View>(R.id.call)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.label.toString() in listOf("Home", "CatalogViewModel")) {
                fab.visibility = View.VISIBLE
            } else {
                fab.visibility = View.GONE
            }
        }
        navView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            true
        }
        navView.setupWithNavController(navController)

        MapKitFactory.setApiKey("bb9c9bdc-48ad-4806-b55d-48c2e98b3b0d")
        MapKitFactory.initialize(this)
    }

    override fun onResume() {
        when(
        getSharedPreferences(App.HOUSE_WITH_SWIMMING_POOL, Context.MODE_PRIVATE)
                .getString(App.SEARCH_ACTIVITY, "false")
        ){
           App.SEARCH_ACTIVITY_TO_CATALOG -> {
                findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.action_navigation_home_to_catalogViewModel)
            }

            App.SEARCH_ACTIVITY_TO_OBJECT -> {
                findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.action_navigation_home_to_houseFragment)
            }
        }
        super.onResume()
    }
}