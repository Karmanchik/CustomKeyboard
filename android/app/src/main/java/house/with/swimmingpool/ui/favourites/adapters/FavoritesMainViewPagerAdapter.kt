package house.with.swimmingpool.ui.favourites.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import house.with.swimmingpool.App
import house.with.swimmingpool.ui.favourites.collectionslist.CollectionsListFragment
import house.with.swimmingpool.ui.favourites.liked.LikedFragment
import house.with.swimmingpool.ui.favourites.searches.SearchesFragment

class FavoritesMainViewPagerAdapter(
        ctx: FragmentActivity,
) : FragmentStateAdapter(ctx) {

    override fun getItemCount(): Int = if (App.setting.user?.context == "agent") 3 else 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LikedFragment()
            }
            1 -> {
                SearchesFragment()
            }
            else -> {
                CollectionsListFragment()
            }
        }
    }
}