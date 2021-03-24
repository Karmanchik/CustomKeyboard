package house.with.swimmingpool.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.databinding.ActivityLoginBinding
import house.with.swimmingpool.ui.register.RegisterLoginFragment
import house.with.swimmingpool.ui.register.registration.RegisterRegistrationFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinging: ActivityLoginBinding

    companion object {
        val fragment = MutableLiveData<Fragment>(RegisterRegistrationFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinging = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinging.root)

        fragment.observe({lifecycle}, ::replaceFragment)

        loginBinging.apply {

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    try {
                        Log.e("test",tab.position.toString())
                        val newFragment = when (tab.position) {
                            0 -> RegisterLoginFragment()
                            else -> RegisterRegistrationFragment()
                        }
                        fragment.postValue(newFragment)
                    } catch (e: Exception) {
                        Log.e("tab", "try", e)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            })
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(loginBinging.frameLogin.id, fragment, fragment::class.java.simpleName)
            .commit()
    }

}