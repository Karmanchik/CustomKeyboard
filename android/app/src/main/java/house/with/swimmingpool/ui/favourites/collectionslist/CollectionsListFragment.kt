package house.with.swimmingpool.ui.favourites.collectionslist

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCollectionsListBinding
import house.with.swimmingpool.models.ShortCollection
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.collection.CollectionFragment
import house.with.swimmingpool.ui.createcollection.CreateCollectionFragment
import house.with.swimmingpool.ui.navigate

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
            navigate(CatalogFragment())
        }

        binding?.apply {
            refresh.setColorSchemeColors(Color.parseColor("#00A8FF"))
            refresh.setOnRefreshListener {
                refresh.isRefreshing = true
                update()
            }
            update()


            showCatalogButton
            textView7.setOnClickListener {
                CreateCollectionFragment.newInstance {
                    update()
                }.show(parentFragmentManager, CreateCollectionFragment::class.java.simpleName)
            }
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
            refresh.isRefreshing = false
            emptyStub.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            content.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        }

        binding?.listRV?.adapter = CollectionsAdapter(
            items = list,
            onItemSearch = {
                val bundle = Bundle().apply { putInt("id", it.id ?: 0) }
                navigate(CollectionFragment(), bundle)
            },
            onDelete = {
                RealtyServiceImpl().deleteCollection(it.id.toString()) { _, _ -> update() }
            },
            parentFragmentManager = parentFragmentManager
        )
    }

}