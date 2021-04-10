package house.with.swimmingpool

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.yandex.mapkit.MapKitFactory
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
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

        //        open house link
        val linkProf = intent.dataString
        if (linkProf != null) {
            val houseIdFromLink = linkProf
                    .substring(linkProf.lastIndexOf("/") + 1)
            Log.e("testLink", "$houseIdFromLink")
            RealtyServiceImpl().getHouseExample(houseIdFromLink.toInt()){data, e, error ->
                if (error == null) {
                    val bundle =
                            Bundle().apply { putString("home", Gson().toJson(data)) }
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_houseFragment_self, bundle)
                }else if(error == 571){
                    Toast.makeText(
                            this,
                            "Объект с таким ID не найден",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else {
            Log.e("testLink", "id = null")
        }

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