package house.with.swimmingpool.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.databinding.ActivityLoginBinding
import house.with.swimmingpool.ui.register.registration.RegisterRegistrationFragment
import house.with.swimmingpool.ui.register.registration.RegisterSmsCodeFragment

class LoginActivity : AppCompatActivity(), ILoginView {

    private lateinit var loginBinging: ActivityLoginBinding

    companion object {
        val tabsFragment = MutableLiveData<Fragment>(RegisterLoginFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinging = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinging.root)

        tabsFragment.observe({ lifecycle }, ::replaceFragmentByTabs)

        loginBinging.apply {

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    try {
                        Log.e("test", tab.position.toString())
                        val newFragment = when (tab.position) {
                            0 -> RegisterLoginFragment()
                            else -> RegisterRegistrationFragment(this@LoginActivity)
                        }
                        tabsFragment.postValue(newFragment)
                    } catch (e: Exception) {
                        Log.e("tab", "try", e)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            })
        }
    }

    fun tabsVisibility(isVisible: Boolean) {
        loginBinging.apply {
            if (isVisible) {
                tabs.visibility = View.VISIBLE
                frameLogin.visibility = View.VISIBLE

                headerWithoutTabs.visibility = View.GONE
                frameLoginWithoutTabs.visibility = View.GONE
            } else {
                tabs.visibility = View.GONE
                frameLogin.visibility = View.GONE


                headerWithoutTabs.visibility = View.VISIBLE
                frameLoginWithoutTabs.visibility = View.VISIBLE
                replaceFragmentWithoutTabs(RegisterSmsCodeFragment())
            }
        }
    }

    private fun replaceFragmentWithoutTabs(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(loginBinging.frameLoginWithoutTabs.id, fragment, fragment::class.java.simpleName)
                .commit()
    }

    private fun replaceFragmentByTabs(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(loginBinging.frameLogin.id, fragment, fragment::class.java.simpleName)
                .commit()
    }

    override fun showSmsCodeFragment(phone: String) {
        tabsVisibility(false)
    }

}