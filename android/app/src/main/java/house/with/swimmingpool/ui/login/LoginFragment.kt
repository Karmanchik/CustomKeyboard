package house.with.swimmingpool.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentLoginBinding
import house.with.swimmingpool.ui.register.RegisterLoginFragment
import house.with.swimmingpool.ui.register.RegisterRegistrationFragment

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var loginBinding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(layoutInflater)
        return loginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<View>(R.id.button).setOnClickListener {
//            App.setting.token = "adfs"
//            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//        }

        loginBinding?.apply {

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    try {
                        val fragment = when (tab.position) {
                            0 -> RegisterLoginFragment()
                            else -> RegisterRegistrationFragment()
                        }
                        Log.e("tab", tab.position.toString())
                        val result = childFragmentManager.beginTransaction()
                            .replace(loginBinding?.frameLogin?.id ?: 0, fragment)
                            .commit()
                        Log.e("tab result", result.toString())
                    } catch (e: Exception) {
                        Log.e("tab", "try", e)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            })
        }

//        childFragmentManager.transaction {
//            replace(R.id.registrationFrame, RegisterFragment())
//        }

    }

    override fun onDestroy() {
        loginBinding = null
        super.onDestroy()
    }

}