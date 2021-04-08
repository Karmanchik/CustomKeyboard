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
        private val parentView: ICabinetView,
) : Fragment(R.layout.fragment_password) {

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

            checkPassword.getField()?.doOnTextChanged { text, start, before, count ->
                buttonSave.isEnabled = checkPassword()
            }

            newPassword.getField()?.doOnTextChanged { text, start, before, count ->
                buttonSave.isEnabled = checkPassword()
            }

            buttonSave.setOnClickListener {
                if (newPassword.value != null && oldPassword.value != null) {
                    if (checkPassword()) {
                        AuthServiceImpl().setPassword(
                                newPassword.value!!,
                                oldPassword.value!!
                        ) { data, e ->
                            if (data != null && e == null) {
                                if (data.error == null) {
                                    App.setting.user = data.data
                                    startActivity(
                                            Intent(requireContext(), PopupActivity::class.java).apply {
                                                putExtra(App.TYPE_OF_POPUP, App.PASSWORD_SET_SUCCESSFULLY)
                                            }
                                    )
                                    parentView.onPasswordSet()
                                } else if (data.error == 773) {
                                    setErrorPassword("Пароль должен быть от 6 до 10 символов.")
                                }
                            } else {
                                oldPassword.setError()
                            }
                        }
                    }
                }
            }
        }

        return passwordBinding?.root
    }

    private fun checkPassword(): Boolean {
        passwordBinding?.apply {
            if (oldPassword.value != "") {
                oldPassword.clearError()
                if (checkPassword.value != "" && newPassword.value != "") {
                    if (checkPassword.value == newPassword.value) {
                        checkPassword.clearError()
                        return true
                    } else {
                        checkPassword.setError()
                        return false
                    }
                } else {
                    checkPassword.clearError()
                    return false
                }
            }else{
                oldPassword.setError()
                return false
            }
        }
        return false
    }

    private fun setErrorPassword(text: String, isVisible: Boolean = true) {
        passwordBinding?.errorPasswordTextView?.apply {
            visibility = if (isVisible) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            this.text = text
        }
    }

    override fun onDestroy() {
        passwordBinding = null
        super.onDestroy()
    }
}