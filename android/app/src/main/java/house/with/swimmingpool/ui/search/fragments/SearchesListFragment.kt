package house.with.swimmingpool.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentSearchesListBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.search.ISearchView
import house.with.swimmingpool.ui.search.adapters.SearchListItemAdapter

class SearchesListFragment(val data: List<HouseCatalogData>, val parentView: ISearchView) : Fragment(){
    private var searchListBinding: FragmentSearchesListBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchListBinding = FragmentSearchesListBinding.inflate(layoutInflater)
        searchListBinding?.searchListItemRv?.adapter = SearchListItemAdapter(requireContext(), parentView,
                if (data.size < 5) {
                    data
                } else {
                    listOf(data[0], data[1], data[2], data[3], data[4])
                }
        )

        return searchListBinding?.root
    }

    override fun onDestroy() {
        searchListBinding = null
        super.onDestroy()
    }
}