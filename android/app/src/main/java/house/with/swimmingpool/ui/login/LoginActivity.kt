package house.with.swimmingpool.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.databinding.ActivityLoginBinding
import house.with.swimmingpool.ui.register.registration.RegisterLoginSuccessFragment
import house.with.swimmingpool.ui.register.registration.RegisterRegistrationFragment
import house.with.swimmingpool.ui.register.registration.RegisterSetPasswordFragment
import house.with.swimmingpool.ui.register.registration.RegisterSmsCodeFragment

class LoginActivity : AppCompatActivity(), ILoginView {

    private lateinit var loginBinging: ActivityLoginBinding

//    companion object {
//        val tabsFragment = MutableLiveData<Fragment>(RegisterLoginFragment(this))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinging = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinging.root)

//        tabsFragment.observe({ lifecycle }, ::replaceFragmentByTabs)

        replaceFragmentByTabs(RegisterLoginFragment(this))

        loginBinging.apply {

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    try {
                        val newFragment = when (tab.position) {
                            0 -> RegisterLoginFragment(this@LoginActivity)
                            else -> RegisterRegistrationFragment(this@LoginActivity)
                        }
//                        tabsFragment.postValue(newFragment)
                        replaceFragmentByTabs(newFragment)
                    } catch (e: Exception) {
                        Log.e("tab", "try", e)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            })

            loginBackIcon.setOnClickListener {
                popBackStack()
            }
        }

        loginBinging.tabs.getTabAt(1)?.select()
        loginBinging.tabs.getTabAt(0)?.select()
    }

    private fun popBackStack(){
        when(supportFragmentManager.fragments.last()){
            is RegisterLoginFragment -> {
                finish()
            }
            is RegisterRegistrationFragment -> {
                finish()
            }
            else -> {
                replaceFragmentByTabs(RegisterLoginFragment(this))
            }
        }
    }

    private fun tabsVisibility(isVisible: Boolean) {
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
            }
        }
    }

    private fun replaceFragmentWithoutTabs(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(
                        loginBinging.frameLoginWithoutTabs.id,
                        fragment,
                        fragment::class.java.simpleName
                )
                .commit()

        tabsVisibility(false)
    }

    private fun replaceFragmentByTabs(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(
                        loginBinging.frameLogin.id,
                        fragment,
                        fragment::class.java.simpleName)
                .commit()
    }

    override fun showSmsCodeFragment(phone: String) {
        replaceFragmentWithoutTabs(RegisterSmsCodeFragment(phone, this@LoginActivity))
    }

    override fun onSmsCodeCorrect() {
        replaceFragmentWithoutTabs(RegisterSetPasswordFragment(this))
    }

    override fun onLoginSuccess(name: String?) {
        replaceFragmentWithoutTabs(RegisterLoginSuccessFragment(name))
    }
}