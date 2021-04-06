package house.with.swimmingpool.ui.register.registration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentRegisterSetPasswordBinding
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.login.LoginActivity
import house.with.swimmingpool.ui.popups.PopupActivity

class RegisterSetPasswordFragment(
        private val parentView: ILoginView,
        private val smsCode: String
): Fragment() {

    private var setPasswordFragmentBinding: FragmentRegisterSetPasswordBinding? = null

    private var isShowPassword = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setPasswordFragmentBinding = FragmentRegisterSetPasswordBinding.inflate(layoutInflater)
        return setPasswordFragmentBinding?.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPasswordFragmentBinding?.apply {

            enterButton.setOnClickListener {
                checkPassword()
            }

            passwordInput.doOnTextChanged { text, start, before, count ->
                errorPasswordTextView.visibility = View.INVISIBLE

                if(text.toString() == ""){
                    iconEye.visibility = View.INVISIBLE
                }else{
                    iconEye.visibility = View.VISIBLE
                }
            }
            passwordInputCheck.doOnTextChanged { text, start, before, count ->
                errorPasswordTextView.visibility = View.INVISIBLE
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

    private fun checkPassword() {
        setPasswordFragmentBinding?.apply {
            if(passwordInput.text.toString() != passwordInputCheck.text.toString()) {
                errorPasswordTextView.visibility = View.VISIBLE
            }else{
                errorPasswordTextView.visibility = View.INVISIBLE

                AuthServiceImpl().setPassword(
                        passwordInputCheck.text.toString(),
                        smsCode
                ) { data, e ->
                    if (data != null && e == null) {
                        App.setting.user = data
                        requireActivity().finish()
                    }else{
                        Log.e("setPasswordError", "$data $e")
                        Toast.makeText(requireContext(), "set password ERROR!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        setPasswordFragmentBinding = null
        super.onDestroy()
    }
}