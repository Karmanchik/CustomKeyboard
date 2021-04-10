package house.with.swimmingpool.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.ui.favourites.collectionslist.CollectionsListFragment
import house.with.swimmingpool.ui.favourites.liked.LikedFragment
import house.with.swimmingpool.ui.favourites.searches.SearchesFragment
import house.with.swimmingpool.ui.home.HomeFragment
import house.with.swimmingpool.ui.navigate

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    companion object {
        var isPopBacLoginActivity = false
        private var lastTabPosition = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = view.findViewById<TabLayout>(R.id.tabs)

        if (App.setting.user?.context != "agent") {
            tabs.removeTabAt(2)
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                lastTabPosition = tab.position
                val fragment = when (tab.position) {
                    0 -> SearchesFragment()
                    1 -> LikedFragment()
                    else -> CollectionsListFragment()
                }
                childFragmentManager.transaction {
                    replace(R.id.frame, fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })

        childFragmentManager.transaction {
            replace(R.id.frame, SearchesFragment())
        }
    }

    override fun onResume() {
        if (isPopBacLoginActivity && !(App.setting.isAuth)) {
            navigate(HomeFragment())
        }

        val tabs = view?.findViewById<TabLayout>(R.id.tabs)
        tabs?.getTabAt(lastTabPosition)?.select()
        super.onResume()
    }


}