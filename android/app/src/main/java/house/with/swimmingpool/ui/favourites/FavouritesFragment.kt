package house.with.swimmingpool.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.google.android.material.tabs.TabLayout
import house.with.swimmingpool.R
import house.with.swimmingpool.ui.favourites.collectionslist.CollectionsListFragment
import house.with.swimmingpool.ui.favourites.houses.HousesFragment
import house.with.swimmingpool.ui.favourites.liked.LikedFragment
import house.with.swimmingpool.ui.favourites.searches.SearchesFragment

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
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

}