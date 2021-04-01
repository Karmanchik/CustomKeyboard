package house.with.swimmingpool.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.databinding.FragmentRegisterLoginBinding

class RegisterLoginFragment(
        private val parentView: ILoginView
        ): Fragment() {

    private var loginBinding: FragmentRegisterLoginBinding? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentRegisterLoginBinding.inflate(layoutInflater)

        return loginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBinding?.apply{
            enterButton.setOnClickListener {
                loginUser(
                        phoneInput.text.toString(),
                        passwordInput.text.toString()
                )
            }
            phoneInput.doOnTextChanged { text, start, before, count ->
                setErrorNotification(isVisible = false)
            }

            passwordInput.doOnTextChanged { text, start, before, count ->
                setErrorNotification(isVisible = false)
            }
        }
    }

    private fun loginUser(phone: String, password: String) {
            if (phone == "+7(000)000-00-00" && password == "test") {
                App.setting.token = "test"
                parentView.onLoginSuccess("Алина")
            }else{
                setErrorNotification("Неверный номер или пароль, попробуйте снова")
            }
    }

    private fun setErrorNotification( text: String = "", isVisible: Boolean = true){
        loginBinding?.apply {
            errorCodeTextView.text = text
            if (isVisible) {
                errorCodeTextView.visibility = View.VISIBLE
            }else{
                errorCodeTextView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        loginBinding = null
        super.onDestroy()
    }
}