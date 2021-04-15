package house.with.swimmingpool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.favourites.FavouritesFragment
import house.with.swimmingpool.ui.home.HomeFragment
import house.with.swimmingpool.ui.login.LoginActivity
import house.with.swimmingpool.ui.startActivity

class MainActivity : AppCompatActivity() {

    private var fragmentIntent: Fragment? = null

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
        try {
            if (!(App.setting.isAuth) && (fragment is CabinetFragment || fragment is FavouritesFragment)) {
                startActivity<LoginActivity> { }
                fragmentIntent = fragment
                return
            }


            if (fragment.isAdded ){
                val transaction = supportFragmentManager.beginTransaction()
                if (inAnim != null && outAnim != null) {
                    transaction.setCustomAnimations(inAnim, outAnim)
                }

                transaction.replace(R.id.mainFrame, fragment.apply { arguments = bundle })
                transaction.addToBackStack(null)
                transaction.commit()
            }else {
                val transaction = supportFragmentManager.beginTransaction()
                if (inAnim != null && outAnim != null) {
                    transaction.setCustomAnimations(inAnim, outAnim)
                }

                transaction.add(R.id.mainFrame, fragment.apply { arguments = bundle })
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } catch (e: Exception) {
            Log.e("testingRestart", "lol", e)
        }
    }

    override fun onResume() {
        super.onResume()
        if (App.setting.isAuth && fragmentIntent != null) {
            showFragment(fragmentIntent!!)
        }
    }

    fun back() {
        onBackPressed()
    }

    fun showModeFab(isShow: Boolean) {
        findViewById<View>(R.id.call).visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun showModeBottomMenu(isShow: Boolean) {
        findViewById<View>(R.id.nav_view).visibility = if (isShow) View.VISIBLE else View.GONE
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, homeFragment)
            .commit()

        navView.setOnNavigationItemSelectedListener {
            val tmp = when (it.itemId) {
                R.id.navigation_home -> homeFragment
                R.id.favouritesFragment -> favouritesFragment
                R.id.catalogViewModel -> catalogFragment
                else -> profileFragment
            }
            showFragment(tmp)
            true
        }
    }
}