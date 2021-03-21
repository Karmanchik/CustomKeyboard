package house.with.swimmingpool.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentRegisterRegistrationBinding

class RegisterRegistrationFragment(): Fragment(R.layout.fragment_register_registration) {
    private var registerBinding: FragmentRegisterRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterRegistrationBinding.inflate(layoutInflater)
        return registerBinding?.root
    }

    override fun onDestroy() {
        registerBinding = null
        super.onDestroy()
    }
}