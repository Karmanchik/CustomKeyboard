package house.with.swimmingpool.ui.cabinet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentCabinetBinding
import house.with.swimmingpool.ui.cabinet.password.PasswordFragment
import house.with.swimmingpool.ui.cabinet.profile.ProfileFragment
import house.with.swimmingpool.ui.login.LoginActivity
import house.with.swimmingpool.ui.startActivity

class CabinetFragment : Fragment(), ICabinetView {

    private var binding: FragmentCabinetBinding? = null

    companion object{
        var isPopBackLoginActivity = false //fix me
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCabinetBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onResume() {
        if (!(App.setting.isAuth) && isPopBackLoginActivity) {
            findNavController().navigate(R.id.action_cabinetFragment_to_navigation_home)
            isPopBackLoginActivity = false
        }
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!(App.setting.isAuth)) {
            requireActivity().startActivity<LoginActivity> {  }
        }

        binding?.apply {
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val fragment = when (tab.position) {
                        0 -> ProfileFragment()
                        else -> PasswordFragment(this@CabinetFragment)
                    }
                    childFragmentManager.transaction {
                        replace(frameCabinet.id, fragment)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            })
        }
    }

    override fun onPasswordSet() {
            binding?.tabs?.getTabAt(0)?.select()
    }

}