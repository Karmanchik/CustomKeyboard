package house.with.swimmingpool.ui.register.registration

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.favourites.FavouritesFragment
import house.with.swimmingpool.ui.favourites.searches.SearchesFragment
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.login.LoginActivity
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.popups.PopupActivity

class RegisterSetPasswordFragment(
        private val parentView: ILoginView,
        private val smsCode: String,
) : Fragment() {

    private var setPasswordFragmentBinding: FragmentRegisterSetPasswordBinding? = null

    private var isShowPassword = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        setPasswordFragmentBinding = FragmentRegisterSetPasswordBinding.inflate(layoutInflater)
        return setPasswordFragmentBinding?.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPasswordFragmentBinding?.apply {

            clearErrorPassword()

            passwordInput.requestFocus()

            enterButton.setOnClickListener {
                checkPassword()
            }

            passwordInput.doOnTextChanged { text, start, before, count ->
                clearErrorPassword()

                if (text.toString() == "") {
                    iconEye.visibility = View.INVISIBLE
                } else {
                    iconEye.visibility = View.VISIBLE
                }
            }
            passwordInputCheck.doOnTextChanged { text, start, before, count ->
                clearErrorPassword()
            }

            iconEye.setOnClickListener {
                isShowPassword = if (!isShowPassword) {
                    iconEye.setImageDrawable(requireActivity()
                            .getDrawable(R.drawable.ic_open_eye_for_password))
                    passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    true
                } else {
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
            if (passwordInput.text.toString() != passwordInputCheck.text.toString()) {
                setErrorPassword("Пароли не совпадают, попробуйте снова")
            } else {
                clearErrorPassword()
                AuthServiceImpl().setPassword(
                        passwordInputCheck.text.toString(),
                        smsCode
                ) { data, e ->
                    if (data != null && e == null) {
                        if (data.error == null) {
                            CabinetFragment.isPopBackLoginActivity = false
                            FavouritesFragment.isPopBacLoginActivity = false
                            requireActivity().finish()
                        } else if (data.error == 773) {
                            setErrorPassword("Введите от 6 до 10 символов (кроме */-.\\”% )")
                        }
                    } else {
                        Log.e("setPasswordError", "$data $e")
//                        TODO("remove toast")
                        Toast.makeText(requireContext(), "set password ERROR! data ${data?.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setErrorPassword(text: String) {
        setPasswordFragmentBinding?.errorPasswordTextView?.apply {
            setTextColor(Color.parseColor("#DB5249"))
            this.text = text
        }
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun clearErrorPassword(){
        setPasswordFragmentBinding?.errorPasswordTextView?.apply {
            text = "Введите от 6 до 10 символов (кроме */-.\\”% )"
            setTextColor(Color.parseColor("#788598"))
        }
    }

    override fun onDestroy() {
        setPasswordFragmentBinding = null
        super.onDestroy()
    }
}