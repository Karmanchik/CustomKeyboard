package house.with.swimmingpool.ui.cabinet.password

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentPasswordBinding
import house.with.swimmingpool.ui.cabinet.ICabinetView
import house.with.swimmingpool.ui.popups.PopupActivity


class PasswordFragment(
        private val parentView: ICabinetView
        ) : Fragment(R.layout.fragment_password){

    private var passwordBinding: FragmentPasswordBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        passwordBinding = FragmentPasswordBinding.inflate(layoutInflater)

        passwordBinding?.apply {
            oldPassword.getField()?.doOnTextChanged { text, start, before, count ->
                oldPassword.clearError()
            }

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
                            if (data != null && e == null) {
                                App.setting.user = data
                                startActivity(
                                        Intent(requireContext(), PopupActivity :: class.java).apply {
                                            putExtra(App.TYPE_OF_POPUP, App.PASSWORD_SET_SUCCESSFULLY)
                                        }
                                )
                                parentView.onPasswordSet()
                            }else{
                                oldPassword.setError()
                            }
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