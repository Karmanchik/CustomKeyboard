package house.with.swimmingpool.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentRegisterLoginBinding
import house.with.swimmingpool.ui.favourites.liked.binding

class RegisterLoginFragment: Fragment() {

    private var loginBinding: FragmentRegisterLoginBinding? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        loginBinding = FragmentRegisterLoginBinding.inflate(layoutInflater)

        loginBinding?.apply {
//            editTextPassword.setOnFocusChangeListener { v, hasFocus ->
//                enterButton.isEnabled = isFieldAreFilled()
//            }
//
//            editTextPhone.setOnFocusChangeListener { v, hasFocus ->
//                enterButton.isEnabled = isFieldAreFilled()
//            }

//            enterButton.setOnClickListener {
//                formEnter.visibility = View.GONE
//                welcomeLayout.visibility = View.VISIBLE
//
//                Handler().postDelayed({
//                    formEnter.visibility = View.VISIBLE
//                    welcomeLayout.visibility = View.GONE
//                }, 3000)
//            }


//                    .setOnFocusChangeListener { v, hasFocus ->
//                Log.e("tag", hasFocus.toString())
//            }
//            setOnClickListener {
//                Log.e("tag", "tag")
//                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
//            }
        }

        return loginBinding?.root
    }

    override fun onDestroy() {
        loginBinding = null
        super.onDestroy()
    }

    private fun isFieldAreFilled(): Boolean {
        loginBinding?.apply {
//            return editTextPhone.text.toString() != "" && editTextPassword.text.toString() != ""
        }
        return false
    }
}