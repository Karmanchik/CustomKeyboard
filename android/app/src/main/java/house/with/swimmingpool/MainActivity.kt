package house.with.swimmingpool

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import house.with.swimmingpool.models.PushInfo
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.favourites.FavouritesFragment
import house.with.swimmingpool.ui.home.HomeFragment
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.login.LoginActivity
import house.with.swimmingpool.ui.startActivity
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer


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
            if (!(App.setting.isAuth) && (fragment is CabinetFragment)) {
                startActivity<LoginActivity> { }
                fragmentIntent = fragment
                return
            }


            if (fragment.isAdded) {
                val transaction = supportFragmentManager.beginTransaction()
                if (inAnim != null && outAnim != null) {
                    transaction.setCustomAnimations(inAnim, outAnim)
                }

                transaction.replace(R.id.mainFrame, fragment.apply { arguments = bundle })
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
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
            fragmentIntent = null
        }
    }

    var client: EmptyClient? = null
    fun connect() {
        if (!App.setting.isAuth) return
        try {
            val url = "wss://domsbasseinom.ru/websocket?client=${App.setting.phone}"

            client = EmptyClient(
                URI.create(url),
                onDisconnect = { connect() },
                onMessageReceived = { message ->
                    createNotification(message)
                }
            )
            client?.connect()
        } catch (e: Exception) {
            Log.e("socket", "socket error")
        }
    }

    override fun onStart() {
        super.onStart()
        try {
            if (App.setting.isAuth) {
                connect()
            }
        } catch (e: Exception) {
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            if (App.setting.isAuth) client?.close()
        } catch (e: Exception) {
        }
    }

    inner class EmptyClient(
        serverURI: URI?,
        private val onDisconnect: () -> Unit,
        private val onMessageReceived: (String) -> Unit
    ) : WebSocketClient(serverURI) {

        override fun onOpen(handshakedata: ServerHandshake) {
            Log.e("socket", "new connection opened")
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
            Log.e("socket", "closed with exit code $code additional info: $reason")
            onDisconnect.invoke()
        }

        override fun onMessage(message: String) {
            Log.e("socket", "received message: $message")
            onMessageReceived.invoke(message)
        }

        override fun onMessage(message: ByteBuffer?) {
            Log.e("socket", "received ByteBuffer")
        }

        override fun onError(ex: java.lang.Exception) {
            Log.e("socket", "an error occurred:$ex")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            data?.getIntExtra("action", 0)?.let { code ->
                if (code == HomeFragment.NAVIGATE_TO_CATALOG) {
                    showFragment(CatalogFragment())
                } else if (code == HomeFragment.NAVIGATE_TO_OBJECT) {
                    val bundle =
                        Bundle().apply { putString("home", Gson().toJson(App.setting.tmpObj)) }
                    showFragment(HouseFragment(), bundle)
                }
            }
        }

//        if (requestCode == HomeFragment.POPUP_WIFI_ERROR_REFRASH) {
//            updateData()
//        }
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
//                            "???????????? ?? ?????????? ID ???? ????????????",
//                            Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }else {
//            Log.e("testLink", "id = null")
//        }

        connect()

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
            if (it.itemId == R.id.catalogViewModel) {
                App.setting.filterConfig = null
            }

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

    private fun showNotify(info: PushInfo, bitmap: Bitmap) {
        try {
            val ids = "12345678"

            val mBuilder: NotificationCompat.Builder?
            val mNotificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intent = Intent(
                "android.intent.action.VIEW",
                Uri.parse(info.action)
            )
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            if (Build.VERSION.SDK_INT >= 26) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                var mChannel = mNotificationManager.getNotificationChannel(ids)
                if (mChannel == null) {
                    mChannel = NotificationChannel(ids, ids, importance)
                    mChannel.enableVibration(true)
                    mChannel.vibrationPattern =
                        longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                    mNotificationManager.createNotificationChannel(mChannel)
                }
                mBuilder = NotificationCompat.Builder(this, ids)
                mBuilder
                    .setLargeIcon(bitmap)
                    .setContentTitle(info.title) // required
                    .setSmallIcon(R.drawable.ic_logo_blue) // required
                    .setContentText(info.description) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setGroup(ids)
                    .setContentIntent(pendingIntent)
                    .setGroupSummary(true)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            } else {
                mBuilder = NotificationCompat.Builder(this)
                    .setLargeIcon(bitmap)
                    .setContentTitle(info.title)
                    .setAutoCancel(true)
                    .setContentText(info.description)
                    .setGroup(ids)
                    .setContentIntent(pendingIntent)
                    .setGroupSummary(true)
                    .setChannelId(ids)
            }

            mNotificationManager.notify(12345678, mBuilder!!.build())
        } catch (e: Exception) {
        }
    }

    private fun createNotification(message: String) {
        Log.e("socket", message)
        val info = Gson().fromJson(message, PushInfo::class.java)
        info?.title?.let {
            Glide.with(this)
                .load(info.photo)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ) = false

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource ?: return false
                        info.title?.let { showNotify(info, resource.toBitmap(80, 80)) }
                        return onResourceReady(resource, model, target, dataSource, isFirstResource)
                    }

                })
                .submit()
        }
    }
}