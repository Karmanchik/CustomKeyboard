package house.with.swimmingpool.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import house.with.swimmingpool.App
import house.with.swimmingpool.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.button).setOnClickListener {
            App.setting.token = "adfs"
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}