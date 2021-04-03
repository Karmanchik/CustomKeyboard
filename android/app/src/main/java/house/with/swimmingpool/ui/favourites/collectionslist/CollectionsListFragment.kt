package house.with.swimmingpool.ui.favourites.collectionslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentCollectionsListBinding
import house.with.swimmingpool.models.CollectionItem

class CollectionsListFragment : Fragment() {

    private var binding: FragmentCollectionsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionsListBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            listRV.adapter = CollectionsAdapter(
                items = listOf(CollectionItem(), CollectionItem()),
                onItemSearch = {
                    val bundle = Bundle().apply { putString("id", "12") }
                    findNavController().navigate(R.id.action_favouritesFragment_to_collectionFragment)
                },
                onOpenMenu = {}
            )

        }
    }

}