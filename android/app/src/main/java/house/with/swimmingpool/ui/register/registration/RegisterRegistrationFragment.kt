package house.with.swimmingpool.ui.register.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentRegisterRegistrationBinding
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.login.LoginFragment

class RegisterRegistrationFragment(val parentView: ILoginView): Fragment() {
    private var registerBinding: FragmentRegisterRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterRegistrationBinding.inflate(layoutInflater)
        return registerBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerBinding?.apply {
            enterButton.setOnClickListener {
                getCodeByPhone(
                        phoneInput.text.toString()
                )
            }
        }
    }

    private fun getCodeByPhone(number: String){
        Toast.makeText(requireContext(), number, Toast.LENGTH_SHORT).show()
        parentView.showSmsCodeFragment(number)
    }

    override fun onStart() {
        super.onStart()
        //LoginFragment.fragment.postValue(CabinetFragment())
    }

    override fun onDestroy() {
        registerBinding = null
        super.onDestroy()
    }
}