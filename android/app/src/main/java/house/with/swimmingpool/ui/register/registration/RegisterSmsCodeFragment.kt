package house.with.swimmingpool.ui.register.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentRegisterSmsCodeBinding
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.noAsterisks

class RegisterSmsCodeFragment(
        private val phone: String,
        private val smsCode: String,
        private val parentView: ILoginView
        ): Fragment() {

    private var registerSmsCodeBinding: FragmentRegisterSmsCodeBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        registerSmsCodeBinding = FragmentRegisterSmsCodeBinding.inflate(layoutInflater)

        return registerSmsCodeBinding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerSmsCodeBinding?.apply {
            titleTextView.text = "Подтвердите номер телефона $phone, введите код из смс"
            inputSmsCod.doOnTextChanged { text, start, before, count ->
                setErrorText(isVisible = false)
                if (text?.noAsterisks()?.length == 4) {
                    if (text.noAsterisks() == smsCode) {
                        parentView.onSmsCodeCorrect()
                    } else {
                        setErrorText("Неверный код, попробуйте снова")
                    }
                }
            }

            refreshButton.setOnClickListener {
                refresh(false)
            }
        }

        setCountDownTimer()
    }

    private fun refresh( isTimeUp: Boolean ){
        registerSmsCodeBinding?.apply {
            if(isTimeUp){
                inputSmsCod.isEnabled = false
                setErrorText("Срок действия кода истек")
                refreshButton.visibility = View.VISIBLE
                timerTextView.visibility = View.GONE
            }else{
                inputSmsCod.isEnabled = true
                inputSmsCod.text?.clear()
                setErrorText(isVisible = false)
                refreshButton.visibility = View.GONE
                timerTextView.visibility = View.VISIBLE
                setCountDownTimer()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCountDownTimer(){
        registerSmsCodeBinding?.apply {
            val timer = object: CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTextView.text = "Код действует еще ${millisUntilFinished / 1000} сек."
                }

                override fun onFinish() {
                    refresh(true)
                }
            }
            timer.start()
        }
    }

    private fun setErrorText(text: String = "", isVisible: Boolean = true) {
        registerSmsCodeBinding?.apply {
            errorCodeTextView.text = text
            if (isVisible) {
                errorCodeTextView.visibility = View.VISIBLE
            } else {
                errorCodeTextView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        registerSmsCodeBinding = null
        super.onDestroy()
    }
}