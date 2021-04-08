package house.with.swimmingpool.ui.register.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentRegisterSmsCodeBinding
import house.with.swimmingpool.ui.login.ILoginView
import house.with.swimmingpool.ui.noDots
import java.lang.Exception

class RegisterSmsCodeFragment(
        private val phone: String,
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

    private var smsCodFromServer : String? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerSmsCodeBinding?.apply {

            inputSmsCod.requestFocus()

            AuthServiceImpl().registerUserFirst(phone) { data, e ->

                titleTextView.text = "Подтвердите номер телефона $phone, введите код из смс"

                if (e == null && data != null) {

                    smsCodFromServer = data.text

                    Toast.makeText(
                            requireContext(),
                            "ваш код $smsCodFromServer",
                            Toast.LENGTH_LONG)
                            .show()

                    inputSmsCod.doOnTextChanged { text, start, before, count ->

                        setErrorText(isVisible = false)

                        Log.e("testingSmsCode", "phone is ${inputSmsCod.text}")

                        if (text?.noDots()?.length == 5) {
                            if ((text.noDots()) == smsCodFromServer) {
                                inputSmsCod.isEnabled = false
                                confirmSmsCodeOnTheServer(text.noDots())
                            } else {
                                if (text.noDots() == text.toString()) {
//                                inputSmsCod.setText("")
                                    Log.e("testingSmsCode", inputSmsCod.text.toString())
                                    Log.e("testingSmsCode", inputSmsCod.rawText)
                                    try {
                                        inputSmsCod.mask = "#####"
                                    }catch (e:Exception){
                                        Log.e("testingSmsCode", e.toString())
                                    }
                                    setErrorText("Неверный код, попробуйте снова")
                                }
                            }
                        }else{
//                            TODO("action if the sms code is not correct")
                        }
                    }

                }else{
//                    TODO("do something if the server have bug")
                    refresh(true)
                    setErrorText("номер уже используется!")
                    getNewSmsCodeButton.visibility = View.GONE
                }
            }
            getNewSmsCodeButton.setOnClickListener {
                getNewSmsCode()
            }
        }

        setCountDownTimer()
    }

    private fun confirmSmsCodeOnTheServer(smsCode: String) {
        AuthServiceImpl().confirmBySmsCode(phone, smsCode) { data, e ->

            if (e == null && data != null) {
                Log.e("RegisterSecond", "user id = ${data.user?.id}")
                App.setting.token = data.token
                App.setting.user = data.user
                parentView.onSmsCodeCorrect(smsCode)
            } else {
//            TODO("do this, if the confirmation of the sms code via the server has failed")
            }
        }
    }

    private fun getNewSmsCode() {
        AuthServiceImpl().getSmsCodeAgain(phone) { data, e ->

            if (e == null && data != null) {
                Toast.makeText(
                        requireContext(),
                        "ваш код ${data.text}",
                        Toast.LENGTH_LONG)
                        .show()
                refresh(false)

                smsCodFromServer = data.text
            }else{
//                TODO( "if the sms code didn't gat from the server")
                Toast.makeText(
                        requireContext(),
                        "SMS SENDING ERROR!",
                        Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    private fun refresh( isTimeUp: Boolean ){
        registerSmsCodeBinding?.apply {
            if(isTimeUp){
                inputSmsCod.isEnabled = false
                setErrorText("Срок действия кода истек")
                getNewSmsCodeButton.visibility = View.VISIBLE
                timerTextView.visibility = View.GONE
            }else{
                inputSmsCod.isEnabled = true
                inputSmsCod.mask = "#####"
                setErrorText(isVisible = false)
                getNewSmsCodeButton.visibility = View.GONE
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