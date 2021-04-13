package house.with.swimmingpool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.favourites.FavouritesFragment
import house.with.swimmingpool.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val favouritesFragment by lazy { FavouritesFragment() }
    private val catalogFragment by lazy { CatalogFragment() }
    private val profileFragment by lazy { CabinetFragment() }


    fun showFragment(
        fragment: Fragment,
        bundle: Bundle? = null,
        @AnimRes inAnim: Int? = null,
        @AnimRes outAnim: Int? = null
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        if (inAnim != null && outAnim != null) {
            transaction.setCustomAnimations(inAnim, outAnim)
        }
        transaction.add(R.id.mainFrame, fragment.apply { arguments = bundle })
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun back() {
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        //        open house link
//        val linkProf = intent.dataString
//        if (linkProf != null) {
//            val houseIdFromLink = linkProf
//                    .substring(linkProf.lastIndexOf("/") + 1)
//            Log.e("testLink", "$houseIdFromLink")
//            RealtyServiceImpl().getHouseExample(houseIdFromLink.toInt()){data, e, error ->
//                if (error == null) {
//                    val bundle =
//                            Bundle().apply { putString("home", Gson().toJson(data)) }
//                    indNavController(R.id.nav_host_fragment).navigate(R.id.action_houseFragment_self, bundle)
//                }else if(error == 571){
//                    Toast.makeText(
//                            this,
//                            "Объект с таким ID не найден",
//                            Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }else {
//            Log.e("testLink", "id = null")
//        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val fab = findViewById<View>(R.id.call)
        fab.setOnClickListener {
            ContextCompat.startActivity(
                this,
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + App.setting.settingPhone)),
                null
            )
        }
        val navController = findViewById<FrameLayout>(R.id.mainFrame)
//        val navController = indNavController(R.id.nav_host_fragment)
//        navController.addOnDestinationChangedListener { f, destination, l ->
//            if (destination.label.toString() in listOf("Home", "CatalogViewModel")) {
//                fab.visibility = View.VISIBLE
//            } else {
//                fab.visibility = View.GONE
//            }

//        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, homeFragment)
            .commit()

        navView.setOnNavigationItemSelectedListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainFrame,
                    when (it.itemId) {
                        R.id.navigation_home -> homeFragment
                        R.id.favouritesFragment -> favouritesFragment
                        R.id.catalogViewModel -> catalogFragment
                        else -> profileFragment
                    }
                )
                .commit()
            true
        }

        try {
            MapKitFactory.setApiKey("bb9c9bdc-48ad-4806-b55d-48c2e98b3b0d")
            MapKitFactory.initialize(this)
        } catch (e: Exception) {
        }
    }
}