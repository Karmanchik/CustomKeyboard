package house.with.swimmingpool.ui.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import house.with.swimmingpool.App
import house.with.swimmingpool.App.Companion.IS_SIGN_OUT
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
        }
    }

    private fun setPopupConsultation(){
        popupBinding.apply {
            singleVariantLayout.visibility = View.VISIBLE
            descriptionSingleVariant.text = getString(R.string.popup_consultation_description)
            buttonSingleVariant.text = "Продолжить"

            buttonSingleVariant.setOnClickListener { finish() }
        }
    }

    private fun setPopupSignOut(){
        popupBinding.apply {
            radButtonLayout.visibility = View.VISIBLE
            descriptionRadButton.text = "Выйти из аккаунта?"
            radButton.text =  "Выйти"
            cancelBesideRadButton.text = "Отмена"

            cancelBesideRadButton.setOnClickListener {
                setResult(RESULT_OK)
                Intent().putExtra(IS_SIGN_OUT, false)
                finish()
            }

            radButton.setOnClickListener {
                setResult(RESULT_OK)
                Intent().putExtra(IS_SIGN_OUT, true)
                finish()
            }
        }
    }
}