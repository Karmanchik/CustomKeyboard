package house.with.swimmingpool.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import house.with.swimmingpool.App
import house.with.swimmingpool.databinding.FragmentFavouritesBinding
import house.with.swimmingpool.ui.favourites.adapters.FavoritesMainViewPagerAdapter
import house.with.swimmingpool.ui.home.HomeFragment
import house.with.swimmingpool.ui.navigate

class FavouritesFragment : Fragment() {

    companion object {
        var isPopBacLoginActivity = false
        private var lastTabPosition = 0
    }

    private var favoritesBinding: FragmentFavouritesBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        favoritesBinding = FragmentFavouritesBinding.inflate(layoutInflater)
        return favoritesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesBinding?.apply {
            favoritesViewPager.adapter = FavoritesMainViewPagerAdapter(requireActivity())

            TabLayoutMediator(tabs, favoritesViewPager) { tab, position ->
                tab.text = when(position){
                    0 -> "ПОИСКИ"
                    1 -> "ИЗБРАННОЕ"
                    else -> "ПОДБОРКИ"
                }
            }.attach()

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    lastTabPosition = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            })
        }
    }

    override fun onResume() {
        if (isPopBacLoginActivity && !(App.setting.isAuth)) {
            navigate(HomeFragment())
        }

        favoritesBinding?.apply {
            tabs.getTabAt(lastTabPosition)?.select()
        }
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesBinding = null
    }
}