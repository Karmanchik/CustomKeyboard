package house.with.swimmingpool.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentRegisterLoginBinding

@SuppressLint("StaticFieldLeak")


class RegisterLoginFragment(): Fragment(R.layout.fragment_register_login) {

    private var loginBinding: FragmentRegisterLoginBinding? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        loginBinding = FragmentRegisterLoginBinding.inflate(layoutInflater)

        loginBinding?.apply {
            editTextPassword.setOnFocusChangeListener { v, hasFocus ->
                enterButton.isEnabled = isFieldAreFilled()
            }

            editTextPhone.setOnFocusChangeListener { v, hasFocus ->
                enterButton.isEnabled = isFieldAreFilled()
            }

//            enterButton.setOnClickListener {
//                formEnter.visibility = View.GONE
//                welcomeLayout.visibility = View.VISIBLE
//
//                Handler().postDelayed({
//                    formEnter.visibility = View.VISIBLE
//                    welcomeLayout.visibility = View.GONE
//                }, 3000)
//            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        loginBinding = null
        super.onDestroy()
    }

    private fun isFieldAreFilled(): Boolean {
        loginBinding?.apply {
            return editTextPhone.text.toString() != "" && editTextPassword.text.toString() != ""
        }
        return false
    }
}