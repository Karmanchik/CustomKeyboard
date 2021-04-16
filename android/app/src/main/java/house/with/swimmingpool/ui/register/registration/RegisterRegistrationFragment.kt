package house.with.swimmingpool.ui.register.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentRegisterRegistrationBinding
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.login.LoginActivity

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
            if (LoginActivity.cashedPhone != null) {
                phoneInput.setText(LoginActivity.cashedPhone)
            }

            signInLater.setOnClickListener {
                requireActivity().finish()
            }

            phoneInput.doOnTextChanged { text, _, _, _ ->
                LoginActivity.cashedPhone = phoneInput.rawText
                setErrorText(isVisible = false)
            }

            enterButton.setOnClickListener {
                checkPhoneNumberForMatches(phoneInput.text.toString())
            }
        }
    }

    private fun checkPhoneNumberForMatches(number: String) {
        registerBinding?.apply {

            if (phoneInput.rawText?.length == 10) {
                AuthServiceImpl().checkPhoneNumber(number) {data, e ->
                    when (data) {
                        706 -> {
                            parentView.showSmsCodeFragment(number)
                        }
                        705 -> {
                            setErrorText("Этот телефон уже зарегистрирован. Попробуйте войти")
                        }
                        else -> {
                            Toast.makeText(
                                    requireContext(),
                                    "ERROR $data",
                                    Toast.LENGTH_LONG)
                                    .show()
                        }
                    }
                }

            } else {
                setErrorText("Введите корректный номер телефона")
            }

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