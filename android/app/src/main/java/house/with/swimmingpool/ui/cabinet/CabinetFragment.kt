package house.with.swimmingpool.ui.cabinet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import house.with.swimmingpool.App
import house.with.swimmingpool.databinding.FragmentCabinetBinding
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.cabinet.adapters.CabinetMainViewPagerAdapter
import house.with.swimmingpool.ui.cabinet.password.PasswordFragment
import house.with.swimmingpool.ui.cabinet.profile.ProfileFragment
import house.with.swimmingpool.ui.home.HomeFragment
import house.with.swimmingpool.ui.login.LoginActivity
import house.with.swimmingpool.ui.navigate
import house.with.swimmingpool.ui.startActivity

class CabinetFragment : Fragment(), ICabinetView {

    private var binding: FragmentCabinetBinding? = null

    companion object {
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
        App.setting.filterConfig = null
        Log.e("testIsLogin", isPopBackLoginActivity.toString())
        if (!(App.setting.isAuth) && isPopBackLoginActivity) {
            navigate(HomeFragment())
            isPopBackLoginActivity = false
            childFragmentManager.fragments.remove(this)
        }
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            cabinetViewPager.adapter = CabinetMainViewPagerAdapter(
                    requireActivity(), this@CabinetFragment)

            TabLayoutMediator(tabs, cabinetViewPager) { tab, position ->
                tab.text = when(position){
                    0 -> "ЛИЧНЫЕ ДАННЫЕ"
                    else -> "СМЕНИТЬ ПАРОЛЬ"
                }
            }.attach()

//            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//                override fun onTabSelected(tab: TabLayout.Tab) {
//                    val fragment = when (tab.position) {
//                        0 -> ProfileFragment()
//                        else -> PasswordFragment(this@CabinetFragment)
//                    }
//                    childFragmentManager.transaction {
//                        replace(frameCabinet.id, fragment)
//                    }
//                }
//
//                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
//
//                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
//
//            })
        }
    }

    override fun onPasswordSet() {
        binding?.tabs?.getTabAt(0)?.select()
    }

}