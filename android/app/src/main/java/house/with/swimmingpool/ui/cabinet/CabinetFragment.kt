package house.with.swimmingpool.ui.cabinet

import android.os.Bundle
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

class CabinetFragment : Fragment() {

    private var binding: FragmentCabinetBinding? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!App.setting.isAuth) {
            findNavController().navigate(R.id.action_cabinetFragment_to_loginFragment)
        }

        binding?.tabs?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> ProfileFragment()
                    else -> PasswordFragment()
                }
                childFragmentManager.transaction {
                    replace(R.id.frame, fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })

    }

}