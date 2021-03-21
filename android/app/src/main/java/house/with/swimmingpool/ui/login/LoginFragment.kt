package house.with.swimmingpool.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.navigation.fragment.findNavController
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentLoginBinding
import house.with.swimmingpool.ui.register.RegisterFragment
import house.with.swimmingpool.ui.register.RegisterLoginFragment
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
        childFragmentManager.transaction {
            replace(R.id.registrationFrame, RegisterFragment())
        }

    }

    override fun onDestroy() {
        loginBinding = null
        super.onDestroy()
    }

}