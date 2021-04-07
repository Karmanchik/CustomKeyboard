package house.with.swimmingpool.ui.favourites.collectionslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCollectionsListBinding
import house.with.swimmingpool.models.ShortCollection

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

        binding?.showCatalogButton?.setOnClickListener {
            findNavController().navigate(R.id.action_favouritesFragment_to_catalogViewModel)
        }

        binding?.apply {
            refresh.setOnRefreshListener {
                refresh.isRefreshing = true
                update()
            }
            update()
        }


    }

    private fun update() {
        RealtyServiceImpl().getCollections { data, e ->
            showData(data ?: listOf())
            binding?.refresh?.isRefreshing = false
        }
    }

    private fun showData(list: List<ShortCollection>) {

        binding?.apply {
            emptyStub.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            content.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        }

        binding?.listRV?.adapter = CollectionsAdapter(
            items = list,
            onItemSearch = {
                val bundle = Bundle().apply { putInt("id", it.id ?: 0) }
                findNavController().navigate(
                    R.id.action_favouritesFragment_to_collectionFragment,
                    bundle
                )
            },
            onOpenMenu = {},
            openCatalog = {
                findNavController().navigate(R.id.action_favouritesFragment_to_catalogViewModel)
            }
        )
    }

}