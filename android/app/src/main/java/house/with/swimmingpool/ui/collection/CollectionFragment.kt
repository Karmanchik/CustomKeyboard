package house.with.swimmingpool.ui.collection

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.FragmentCollectionBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.back
import house.with.swimmingpool.ui.catalog.CatalogFragment
import house.with.swimmingpool.ui.home.adapters.CatalogAdapter
import house.with.swimmingpool.ui.house.HouseFragment
import house.with.swimmingpool.ui.navigate

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

    val id: String get() = (arguments?.getInt("id") ?: 0).toString()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            main.setOnClickListener {
                sortMenu.visibility = View.GONE
            }

            openNote.setOnClickListener {
                note.visibility = if (note.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                showNoteText.text =
                    if (note.visibility == View.VISIBLE) "Скрыть заметку" else if(noteValue.text.isEmpty()) "Добавить заметку" else "Посмотреть заметку"

                if (note.visibility == View.VISIBLE) {
                    val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.search_enter_animation)
                    note.startAnimation(anim)
                }
            }

            toolbar.setOnClickListener {
                sortMenu.visibility = View.GONE
            }
            mainRV.setOnScrollChangeListener { _, _, _, _, _ ->
                sortMenu.visibility = View.GONE
            }

            refresh.isRefreshing = true
            RealtyServiceImpl().getCollection(id) { data, e ->
                conter.text = "${data?.name ?: ""},\n${data?.objects?.size ?: 0} объектов"
                refresh.isRefreshing = false
                showData(data?.objects ?: listOf())

                note.setOnClickListener {
                    DialogEditNoteFragment.newInstance(
                        text = noteValue.text.toString(),
                        onEnterText = {
                            noteValue.text = it
                            RealtyServiceImpl().changeNoteInCollection(id, it) { _, _ -> }
                        }
                    ).show(parentFragmentManager, "DialogEditNoteFragment")
                }
                noteValue.text = data?.note

                showNoteText.text =
                    if (note.visibility == View.VISIBLE) "Скрыть заметку" else if(noteValue.text.isEmpty()) "Добавить заметку" else "Посмотреть заметку"


                share.setOnClickListener {
                    sortMenu.visibility = View.GONE
                    val share = Intent(Intent.ACTION_SEND)
                    share.type = "text/plain"
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                    share.putExtra(Intent.EXTRA_SUBJECT, "Подборка ${data?.name ?: ""}")
                    share.putExtra(Intent.EXTRA_TEXT, "https://domsbasseinom.ru${data?.link ?: ""}")
                    startActivity(Intent.createChooser(share, "Поделиться!"))
                }

                deleteB.setOnClickListener {
                    RealtyServiceImpl().deleteCollection(data?.id.toString()) { data, e ->
                        sortMenu.visibility = View.GONE
                        data?.let { back() }
                    }
                }

                shareB.setOnClickListener {
                    sortMenu.visibility = View.GONE
                    val share = Intent(Intent.ACTION_SEND)
                    share.type = "text/plain"
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                    share.putExtra(Intent.EXTRA_SUBJECT, "Подборка ${data?.name ?: ""}")
                    share.putExtra(Intent.EXTRA_TEXT, "https://domsbasseinom.ru${data?.link ?: ""}")
                    startActivity(Intent.createChooser(share, "Поделиться!"))
                }

                noteAdd.setOnClickListener {
                    sortMenu.visibility = View.GONE
                    DialogEditNoteFragment.newInstance(
                        text = noteValue.text.toString(),
                        onEnterText = {
                            noteValue.text = it
                            RealtyServiceImpl().changeNoteInCollection(id, it) { _, _ ->
                                showNoteText.text = "Скрыть заметку"
                            }
                        }
                    ).show(parentFragmentManager, "DialogEditNoteFragment")
                }

                dateCreated.text = "Дата создания ${data?.date ?: ""}"
            }

            closeNote.setOnClickListener {
                noteValue.text = ""
                RealtyServiceImpl().changeNoteInCollection(id, "") { _, _ ->
                    openNote.performClick()
                }
            }

            back.setOnClickListener {
                back()
            }


            add.setOnClickListener {
                sortMenu.visibility = View.VISIBLE
            }

            closeSort.setOnClickListener {
                sortMenu.visibility = View.GONE
            }

        }
    }

    private fun showData(list: List<HouseCatalogData>) {
        binding?.toCatalog?.setOnClickListener {
            navigate(CatalogFragment())
        }

        binding?.stub?.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE

        binding?.mainRV?.adapter =
            CatalogAdapter(list.map { it as Any }, requireContext()) { homeId ->
                val home = list.firstOrNull { it.id == homeId }
                val bundle = Bundle().apply { putString("home", Gson().toJson(home)) }
                navigate(HouseFragment(), bundle)
            }
    }

}