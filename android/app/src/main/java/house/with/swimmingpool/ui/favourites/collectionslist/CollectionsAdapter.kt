package house.with.swimmingpool.ui.favourites.collectionslist

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.ItemCollectionBinding
import house.with.swimmingpool.models.ShortCollection
import house.with.swimmingpool.ui.SmallPhotosAdapter
import house.with.swimmingpool.ui.collection.DialogEditNoteFragment

class CollectionsAdapter(
    var items: List<ShortCollection>,
    var onItemSearch: (ShortCollection) -> Unit,
    val parentFragmentManager: FragmentManager,
    val onDelete: (ShortCollection) -> Unit
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    private var closeMenu: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCollectionBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(private val view: ItemCollectionBinding) :
        RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ShortCollection) {
            itemView.setOnClickListener {
                closeMenu?.invoke()
                onItemSearch.invoke(item)
            }
            view.textView8.setOnClickListener {
                closeMenu?.invoke()
                onItemSearch.invoke(item)
            }
            view.name.text = item.name
            view.counter.text = item.total
            view.menu.setOnClickListener {
                closeMenu?.invoke()
                closeMenu = {
                    view.sortMenu.visibility = View.GONE
                }
                view.sortMenu.visibility = View.VISIBLE
            }
            view.closeSort.setOnClickListener {
                view.sortMenu.visibility = View.GONE
            }
            view.shareB.setOnClickListener {
                closeMenu?.invoke()
                view.sortMenu.visibility = View.GONE
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                share.putExtra(Intent.EXTRA_SUBJECT, "Подборка ${item.name ?: ""}")
                share.putExtra(Intent.EXTRA_TEXT, "https://domsbasseinom.ru${item.link ?: ""}")
                itemView.context.startActivity(Intent.createChooser(share, "Поделиться!"))
            }
            view.deleteB.setOnClickListener {
                closeMenu?.invoke()
                onDelete.invoke(item)
            }
            view.noteAdd.setOnClickListener {
                closeMenu?.invoke()
                view.sortMenu.visibility = View.GONE
                DialogEditNoteFragment.newInstance(
                    text = item.description,
                    onEnterText = {
                        RealtyServiceImpl().changeNoteInCollection(item.id.toString(), it) { _, _ -> }
                    }
                ).show(parentFragmentManager, "DialogEditNoteFragment")
            }
            view.dateCreated.text = "Дата создания ${item.date ?: ""}"
            view.mediaRV.layoutManager =
                GridLayoutManager(itemView.context, 4)
            view.mediaRV.adapter = SmallPhotosAdapter(item.photos?.take(4) ?: listOf())
            view.mediaRV.visibility = if (item.photos.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

}