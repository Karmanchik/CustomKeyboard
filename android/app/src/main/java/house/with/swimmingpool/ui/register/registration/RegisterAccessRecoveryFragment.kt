package house.with.swimmingpool.ui.register.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import house.with.swimmingpool.api.config.controllers.AuthServiceImpl
import house.with.swimmingpool.databinding.FragmentRegisterAccessRecoveryBinding
import house.with.swimmingpool.ui.login.ILoginView

class RegisterAccessRecoveryFragment( val parentView: ILoginView): Fragment() {

    private var binding: FragmentRegisterAccessRecoveryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            enterButton.setOnClickListener {
                checkPhoneNumberForMatches(phoneInput.text.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterAccessRecoveryBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun checkPhoneNumberForMatches(number: String) {
        binding?.apply {

            if (phoneInput.rawText?.length == 10) {
//                AuthServiceImpl().checkPhoneNumber(number) { data, e ->
//                    when (data) {
//                        706 -> {
//                            parentView.showSmsCodeFragment(number)
//                        }
//                        705 -> {
//                            setErrorText("Этот телефон уже зарегистрирован. Попробуйте войти")
//                        }
//                        else -> {
//                            Toast.makeText(
//                                requireContext(),
//                                "ERROR $data",
//                                Toast.LENGTH_LONG)
//                                .show()
//                        }
//                    }
//                }
                AuthServiceImpl().resetPassword(number){ data, e ->
                    if(data != null && e == null) {
                        Log.e("testResetPassword", data.toString())
                        parentView.showSmsCodeFragment(number)
                    }else if(e == 706){
                        setErrorText("Этот номер еще не зарегистрирован в системе.")
                    } else {
                        setErrorText("Введите корректный номер телефона")
                    }
                }
            } else {
                setErrorText("Введите корректный номер телефона")
            }

        }
    }

    private fun setErrorText(text: String = "", isVisible: Boolean = true) {
        binding?.apply {
            errorPhoneTextView.text = text
            if (isVisible) {
                errorPhoneTextView.visibility = View.VISIBLE
            } else {
                errorPhoneTextView.visibility = View.INVISIBLE
            }
        }
    }
}