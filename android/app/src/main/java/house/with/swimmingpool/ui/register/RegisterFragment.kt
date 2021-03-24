package house.with.swimmingpool.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentRegisterBinding

@SuppressLint("StaticFieldLeak")
private var registerBinding: FragmentRegisterBinding? = null

class RegisterFragment : Fragment(R.layout.fragment_register){

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        registerBinding = FragmentRegisterBinding.inflate(layoutInflater)

        return registerBinding?.root
    }


    override fun onDestroy() {
        registerBinding = null
        super.onDestroy()
    }
//    private fun isFieldAreFilled() : Boolean{
//        registerBinding.apply {
//            return editTextPhone.text.toString() != "" && editTextPassword.text.toString() != ""
//        }
//    }

}