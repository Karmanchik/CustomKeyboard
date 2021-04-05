package house.with.swimmingpool

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.favourites.FavouritesFragment
import house.with.swimmingpool.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

//    private val homeFragment by lazy { HomeFragment() }
//    private val favouritesFragment by lazy { FavouritesFragment() }
//    private val catalogFragment by lazy { CatalogFragment() }
//    private val profileFragment by lazy { CabinetFragment() }

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
//            navController.
            navController.navigate(it.itemId)
            true
        }
        navView.setupWithNavController(navController)

        try {
            MapKitFactory.setApiKey("bb9c9bdc-48ad-4806-b55d-48c2e98b3b0d")
            MapKitFactory.initialize(this)
        } catch (e: Exception) {
        }
    }
}