package house.with.swimmingpool.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentSearchTagButtonBinding
import house.with.swimmingpool.ui.house.adapters.WhiteButtonAdapter
import house.with.swimmingpool.ui.search.ISearchView
import house.with.swimmingpool.ui.search.adapters.SearchTagAdapter

class SearchTagButtonFragment(
    private val parent: ISearchView,
    private val listItems: List<String>) : Fragment(){
    private var searchTagBinding: FragmentSearchTagButtonBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchTagBinding = FragmentSearchTagButtonBinding.inflate(layoutInflater)

        searchTagBinding?.tagRV?.adapter = SearchTagAdapter(requireContext(), parent, listItems)

        return searchTagBinding?.root
    }

    override fun onDestroy() {
        searchTagBinding = null
        super.onDestroy()
    }
}