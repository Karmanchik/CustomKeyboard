package house.with.swimmingpool.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentLoginBinding
import house.with.swimmingpool.ui.favourites.houses.HousesFragment
import house.with.swimmingpool.ui.favourites.liked.LikedFragment
import house.with.swimmingpool.ui.favourites.searches.SearchesFragment
import house.with.swimmingpool.ui.register.RegisterFragment
import house.with.swimmingpool.ui.register.RegisterLoginFragment
import house.with.swimmingpool.ui.register.RegisterRegistrationFragment
import kotlin.math.log

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
                    val fragment = when (tab.position) {
                        0 -> RegisterLoginFragment()
                        else -> RegisterRegistrationFragment()
                    }
                    childFragmentManager.transaction {
                        replace(R.id.frame, fragment)
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