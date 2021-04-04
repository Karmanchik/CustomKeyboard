package house.with.swimmingpool.ui.search.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentSearchesListBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.search.ISearchView
import house.with.swimmingpool.ui.search.adapters.SearchListItemAdapter

class SearchesListFragment(
    val data: List<HouseCatalogData>,
    private val parentView: ISearchView
) : Fragment() {

    private var searchListBinding: FragmentSearchesListBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchListBinding = FragmentSearchesListBinding.inflate(layoutInflater)
        searchListBinding?.searchListItemRv?.adapter =
            SearchListItemAdapter(requireContext(), parentView, data.take(5))
        searchListBinding?.objCounter?.text = "Все объкты (${data.size})"

        return searchListBinding?.root
    }

    override fun onDestroy() {
        searchListBinding = null
        super.onDestroy()
    }
}