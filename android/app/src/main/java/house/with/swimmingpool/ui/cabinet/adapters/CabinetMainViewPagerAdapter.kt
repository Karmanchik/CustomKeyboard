package house.with.swimmingpool.ui.cabinet.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import house.with.swimmingpool.ui.cabinet.ICabinetView
import house.with.swimmingpool.ui.cabinet.password.PasswordFragment
import house.with.swimmingpool.ui.cabinet.profile.ProfileFragment

class CabinetMainViewPagerAdapter(
        private val ctx: FragmentActivity,
        private val parentView: ICabinetView,
) : FragmentStateAdapter(ctx) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProfileFragment()
            }
            else -> {
                PasswordFragment(parentView)
            }
        }
    }
}