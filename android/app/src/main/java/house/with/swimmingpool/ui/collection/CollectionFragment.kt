package house.with.swimmingpool.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCollectionBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter

class CollectionFragment : Fragment() {

    private var binding: FragmentCollectionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            openNote.setOnClickListener {
                note.visibility = if (note.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                showNoteText.text = if (note.visibility == View.VISIBLE) "Скрыть заметку" else "Посмотреть заметку"
            }

            RealtyServiceImpl().getCollection((arguments?.getInt("id") ?: 0).toString()) { data, e ->
                showData(data?.objects ?: listOf())
                note.setOnClickListener {
                    DialogEditNoteFragment.newInstance(data?.description) {

                    }.show(parentFragmentManager, "DialogEditNoteFragment")
                }
            }
        }
    }

    private fun showData(list: List<HouseCatalogData>) {
        binding?.mainRV?.adapter = CatalogAdapter(list.map { it as Any }, requireContext()) { homeId ->
                val home = list.firstOrNull { it.id == homeId }
                val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                findNavController().navigate(
                    R.id.action_favouritesFragment_to_houseFragment,
                    bundle
                )
            }
    }

}