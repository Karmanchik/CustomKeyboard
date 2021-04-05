package house.with.swimmingpool.ui.register.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentRegisterLoginSuccessBinding

class RegisterLoginSuccessFragment (val name : String?): Fragment() {

    private var loginSuccessBinding: FragmentRegisterLoginSuccessBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginSuccessBinding = FragmentRegisterLoginSuccessBinding.inflate(layoutInflater)

        return loginSuccessBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (name != null && name != "") {
            loginSuccessBinding?.greetingTextView?.text = "С возвращением, $name!"
        } else {
            loginSuccessBinding?.greetingTextView?.text = "С возвращением!"
        }

        Handler().postDelayed({
            requireActivity().finish()
        }, 2000)

    }

    override fun onDestroy() {
        loginSuccessBinding = null
        super.onDestroy()
    }
}