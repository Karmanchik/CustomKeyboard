package house.with.swimmingpool.ui.cabinet.password

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentPasswordBinding
import house.with.swimmingpool.models.User
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

        clearErrorPassword()

        passwordBinding?.apply {
            oldPassword.getField()?.doOnTextChanged { text, start, before, count ->
                buttonSave.isEnabled = checkPassword()
            }

            newPasswordConfirmation.getField()?.doOnTextChanged { text, start, before, count ->
                buttonSave.isEnabled = checkPassword()
            }

            newPassword.getField()?.doOnTextChanged { text, start, before, count ->
                buttonSave.isEnabled = checkPassword()
            }

            buttonSave.setOnClickListener {
                setPassword()
            }
        }

        return passwordBinding?.root
    }

    private fun checkPassword(): Boolean {
        passwordBinding?.apply {
            clearErrorPassword()
            return (oldPassword.isNotNullOrEmpty()
                && newPassword.isNotNullOrEmpty()
                && newPasswordConfirmation.isNotNullOrEmpty()
                && checkPasswordOnMatches()
            )
        }
        return false
    }

    private fun checkPasswordOnMatches(): Boolean {
        passwordBinding?.apply {
            return if (newPasswordConfirmation.value == newPassword.value) {
                true
            } else {
                newPasswordConfirmation.setError()
                false
            }
        }
        return false
    }

    private fun setPassword() {
        passwordBinding?.apply {
            AuthServiceImpl().setPassword(
                newPassword.value!!,
                oldPassword.value!!
            ) { data, e ->
                if (data != null && e == null) {
                    when (data.error) {
                        null -> {
                            onPasswordSated(data.data)
                        }
                        773 -> {
                            setErrorPassword("Введите от 6 до 10 символов (кроме */-.\\”% )")
                        }
                        772 -> {
                            setErrorPassword("Не верный пароль")
                        }
                    }
                }
            }
        }
    }

    private fun onPasswordSated(user: User) {
        App.setting.user = user
        startActivity(
            Intent(
                requireContext(),
                PopupActivity::class.java
            ).apply {
                putExtra(
                    App.TYPE_OF_POPUP,
                    App.PASSWORD_SET_SUCCESSFULLY
                )
            }
        )
        parentView.onPasswordSet()
    }

    @SuppressLint("ResourceAsColor")
    private fun setErrorPassword(text: String) {
        passwordBinding?.errorPasswordText?.apply {
            this.text = text
            setTextColor(Color.parseColor("#DB5249"))
        }
        passwordBinding?.buttonSave?.isEnabled = false
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun clearErrorPassword() {
        passwordBinding?.errorPasswordText?.apply {
            text = "Введите от 6 до 10 символов (кроме */-.\\”% )"
            setTextColor(Color.parseColor("#788598"))
        }
    }

    override fun onPause() {
        super.onPause()
        passwordBinding?.apply {
            oldPassword.value = ""
            newPassword.value = ""
            newPasswordConfirmation.value = ""

            oldPassword.clearError()
            newPassword.clearError()
            newPasswordConfirmation.clearError()
            clearErrorPassword()
        }
    }

    override fun onDestroy() {
        passwordBinding = null
        super.onDestroy()
    }
}