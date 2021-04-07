package house.with.swimmingpool.ui.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import house.with.swimmingpool.App
import house.with.swimmingpool.App.Companion.INTERNET_ERROR
import house.with.swimmingpool.App.Companion.IS_SIGN_OUT
import house.with.swimmingpool.App.Companion.PASSWORD_SET_SUCCESSFULLY
import house.with.swimmingpool.App.Companion.SEND_REQUEST_CONSULTATION
import house.with.swimmingpool.App.Companion.SIGN_OUT
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ActivityPopupBinding

class PopupActivity : AppCompatActivity() {

    lateinit var popupBinding: ActivityPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO("on real device popup have black background")
        popupBinding = ActivityPopupBinding.inflate(layoutInflater)
        setContentView(popupBinding.root)

        when(intent.getStringExtra(App.TYPE_OF_POPUP)){
            SEND_REQUEST_CONSULTATION -> {
              setPopupConsultation()
            }

            SIGN_OUT -> {
                setPopupSignOut()
            }

            PASSWORD_SET_SUCCESSFULLY -> {
                setPopupPasswordChanged()
            }

            INTERNET_ERROR ->{
                setPopupInternetError()
            }
        }
    }

    private fun setPopupInternetError(){
        popupBinding.apply {
            singleVariantLayout.visibility = View.VISIBLE
            descriptionSingleVariant.text = getString(R.string.popup_internet_error_description)
            buttonSingleVariant.text = "Повторить"

            buttonSingleVariant.setOnClickListener {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun setPopupConsultation(){
        popupBinding.apply {
            radButtonLayout.visibility = View.VISIBLE
            descriptionRadButton.text = getString(R.string.popup_consultation_description)
            radButton.visibility = View.GONE
            cancelBesideRadButton.text = "Продолжить"

            buttonSingleVariant.setOnClickListener { finish() }
        }
    }

    private fun setPopupPasswordChanged(){
        popupBinding.apply {
            radButtonLayout.visibility = View.VISIBLE
            descriptionRadButton.text = "Ваш пароль успешно изменен"
            radButton.visibility = View.GONE
            cancelBesideRadButton.text = "ОК"

            cancelBesideRadButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun setPopupSignOut(){
        popupBinding.apply {
            radButtonLayout.visibility = View.VISIBLE
            descriptionRadButton.text = "Выйти из аккаунта?"
            radButton.text =  "Выйти"
            cancelBesideRadButton.text = "Отмена"

            cancelBesideRadButton.setOnClickListener {
                setResult(RESULT_OK,
                Intent().putExtra(IS_SIGN_OUT, false))
                finish()
            }

            radButton.setOnClickListener {
                setResult(RESULT_OK,
                Intent().putExtra(IS_SIGN_OUT, true))
                finish()
            }
        }
    }
}