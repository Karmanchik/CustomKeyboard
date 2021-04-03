package house.with.swimmingpool.ui.popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import house.with.swimmingpool.App
import house.with.swimmingpool.App.Companion.SEND_REQUEST_CONSULTATION
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ActivityPopupBinding

class PopupActivity : AppCompatActivity() {

    lateinit var popupBinding: ActivityPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popupBinding = ActivityPopupBinding.inflate(layoutInflater)
        setContentView(popupBinding.root)

        when(intent.getStringExtra(App.TYPE_OF_POPUP)){
            SEND_REQUEST_CONSULTATION -> {
              setPopupConsultation()
            }
        }
    }

    private fun setPopupConsultation(){
        popupBinding.apply {
            singleVariantLayout.visibility = View.VISIBLE
            multiVariantLayout.visibility = View.GONE
            descriptionSingleVariant.text = getString(R.string.popup_consultation_description)
            buttonSingleVariant.text = "Продолжить"

            buttonSingleVariant.setOnClickListener { finish() }
        }
    }
}