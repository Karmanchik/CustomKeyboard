package house.with.swimmingpool.ui.cabinet.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment(R.layout.fragment_password){

    private var passwordBinding: FragmentPasswordBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        passwordBinding = FragmentPasswordBinding.inflate(layoutInflater)

        passwordBinding?.apply {
//            checkPassword.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//                Log.e("tag",  "hasFocus.toString()")
//                if (!hasFocus) {
//                    if (checkPassword != newPassword) {
//                        checkPassword.setError()
//                    } else {
//                        checkPassword.clearError()
//                    }
//                }
//            }
//
//            newPassword.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//                Log.e("tag",  "hasFocus.toString()")
//                if (!hasFocus){
//                   if (newPassword.isEnabled){
//                       newPassword.setError()
//                   }else{
//                       newPassword.clearError()
//                   }
//                }
//            }
        }

        return passwordBinding?.root
    }

    override fun onDestroy() {
        passwordBinding = null
        super.onDestroy()
    }
}