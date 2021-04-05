package house.with.swimmingpool.ui.cabinet.password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
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
            checkPassword.getField()?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                checkPassword()
            }

            newPassword.getField()?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                checkPassword()
            }

            buttonSave.setOnClickListener {
                if (newPassword.value != null && oldPassword.value != null) {
                    if(checkPassword()) {
                        AuthServiceImpl().setPassword(
                                newPassword.value!!,
                                oldPassword.value!!
                        ) { data, e ->
                            App.setting.user = data
                        }
                    }
                }
            }
        }

        return passwordBinding?.root
    }

    private fun checkPassword(): Boolean{
        passwordBinding?.apply {
            return if (checkPassword.value != "" && newPassword.value != ""){
                if(checkPassword.value == newPassword.value){
                    checkPassword.clearError()
                    true
                }else{
                    checkPassword.setError()
                    false
                }
            }else{
                checkPassword.clearError()
                false
            }
        }
        return false
    }

    override fun onDestroy() {
        passwordBinding = null
        super.onDestroy()
    }
}