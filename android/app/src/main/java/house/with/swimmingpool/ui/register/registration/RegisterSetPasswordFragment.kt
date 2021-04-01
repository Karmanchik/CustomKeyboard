package house.with.swimmingpool.ui.register.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import house.with.swimmingpool.App
import house.with.swimmingpool.databinding.FragmentRegisterSetPasswordBinding
import house.with.swimmingpool.ui.login.ILoginView

class RegisterSetPasswordFragment(private val parentView: ILoginView): Fragment() {

    private var setPasswordFragmentBinding: FragmentRegisterSetPasswordBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setPasswordFragmentBinding = FragmentRegisterSetPasswordBinding.inflate(layoutInflater)
        return setPasswordFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPasswordFragmentBinding?.apply {
            enterButton.setOnClickListener {
                checkPassword()
            }

            passwordInput.doOnTextChanged { text, start, before, count ->
                errorPasswordTextView.visibility = View.INVISIBLE
            }
            passwordInputCheck.doOnTextChanged { text, start, before, count ->
                errorPasswordTextView.visibility = View.INVISIBLE
            }
        }
    }

    private fun checkPassword() {
        setPasswordFragmentBinding?.apply {
            if(passwordInput.text.toString() != passwordInputCheck.text.toString()) {
                errorPasswordTextView.visibility = View.VISIBLE
            }else{
                errorPasswordTextView.visibility = View.INVISIBLE
                App.setting.token = "test"
                requireActivity().finish()
            }
        }
    }

    override fun onDestroy() {
        setPasswordFragmentBinding = null
        super.onDestroy()
    }
}