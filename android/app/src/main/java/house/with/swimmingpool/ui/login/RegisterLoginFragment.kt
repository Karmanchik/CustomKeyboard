package house.with.swimmingpool.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentRegisterLoginBinding

class RegisterLoginFragment(
        private val parentView: ILoginView
        ): Fragment() {

    private var isShowPassword = false

    private var loginBinding: FragmentRegisterLoginBinding? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentRegisterLoginBinding.inflate(layoutInflater)

        return loginBinding?.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE )
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        loginBinding?.apply{

            signInLater.setOnClickListener {
                requireActivity().finish()
            }

            if (LoginActivity.cashedPhone != null) {
                phoneInput.setText(LoginActivity.cashedPhone)
            }

            phoneInput.doOnTextChanged { text, start, before, count ->
                LoginActivity.cashedPhone = phoneInput.rawText
                setErrorNotification(isVisible = false)
                isFieldsAreFilled()
            }

            passwordInput.doOnTextChanged { text, start, before, count ->
                setErrorNotification(isVisible = false)
                isFieldsAreFilled()

                if(text.toString() == ""){
                    iconEye.visibility = View.INVISIBLE
                }else{
                    iconEye.visibility = View.VISIBLE
                }
            }

            iconEye.setOnClickListener {
                isShowPassword = if (!isShowPassword){
                    iconEye.setImageDrawable(requireActivity()
                            .getDrawable(R.drawable.ic_open_eye_for_password))
                    passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    true
                }else {
                    iconEye.setImageDrawable(requireActivity()
                            .getDrawable(R.drawable.ic_close_eye_for_password))
                    passwordInput.inputType = 129
                    false
                }
            }


        }
    }

    private fun isFieldsAreFilled(){
        loginBinding?.apply {
            if (phoneInput.rawText?.length == 10 && passwordInput.text.toString() != ""){
                enterButton.isEnabled = true
                enterButton.setOnClickListener {
                    loginUser(
                            phoneInput.text.toString(),
                            passwordInput.text.toString()
                    )
                }
            }else{
                enterButton.isEnabled = false
            }
        }
    }

    private fun loginUser(phone: String, password: String) {
        AuthServiceImpl().loginUser(phone, password,)
        { data, e ->
            Log.e("loginTest", "data : $data")
            if (e == null && data != null) {
                App.setting.token = data.token
                App.setting.user = data.user
                parentView.onLoginSuccess(data.user?.name)
            } else {
                Log.e("loginTest", "error", e)
                setErrorNotification("Неверный номер или пароль, попробуйте снова")
            }
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