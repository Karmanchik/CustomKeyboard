package house.with.swimmingpool.ui.register.registration

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentRegisterRegistrationBinding
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.login.LoginFragment

class RegisterRegistrationFragment(private val parentView: ILoginView) : Fragment() {

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
                if (phoneInput.text?.length == 16) {
                    getCodeByPhone(phoneInput.text.toString())
                }
                phoneInput.doOnTextChanged { _, _, _, _ ->
                    setErrorText(isVisible = false)
                }
            }
        }
    }

    private fun getCodeByPhone(number: String) {
        Toast.makeText(requireContext(), "sms code is 0000", Toast.LENGTH_SHORT).show()
        if (number != "+7(000)000-00-00") {
            parentView.showSmsCodeFragment(number)
        } else {
            setErrorText("Этот телефон уже зарегистрирован. Попробуйте войти")
        }
    }

    private fun setErrorText(text: String = "", isVisible: Boolean = true) {
        registerBinding?.apply {
            errorPhoneTextView.text = text
            if (isVisible) {
                errorPhoneTextView.visibility = View.VISIBLE
            } else {
                errorPhoneTextView.visibility = View.INVISIBLE
            }
        }
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