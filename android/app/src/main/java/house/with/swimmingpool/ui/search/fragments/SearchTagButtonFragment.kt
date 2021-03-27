package house.with.swimmingpool.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.databinding.FragmentSearchTagButtonBinding
import house.with.swimmingpool.ui.house.adapters.WhiteButtonAdapter

class SearchTagButtonFragment : Fragment(){
    private var searchTagBinding: FragmentSearchTagButtonBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchTagBinding = FragmentSearchTagButtonBinding.inflate(layoutInflater)

        searchTagBinding?.tagRV?.adapter = WhiteButtonAdapter(requireContext(), )

        return searchTagBinding?.root
    }

    override fun onDestroy() {
        searchTagBinding = null
        super.onDestroy()
    }
}