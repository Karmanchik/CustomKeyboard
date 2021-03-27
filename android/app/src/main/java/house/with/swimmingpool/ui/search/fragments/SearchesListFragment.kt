package house.with.swimmingpool.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentSearchesListBinding
import house.with.swimmingpool.ui.search.adapters.SearchListItemAdapter

class SearchesListFragment : Fragment(){
    private var searchListBinding: FragmentSearchesListBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchListBinding = FragmentSearchesListBinding.inflate(layoutInflater)

        searchListBinding?.searchListItemRv?.adapter = SearchListItemAdapter(requireContext(), listOf("", "", "", "", ""))

        return searchListBinding?.root
    }

    override fun onDestroy() {
        searchListBinding = null
        super.onDestroy()
    }
}