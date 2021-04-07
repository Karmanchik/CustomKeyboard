package house.with.swimmingpool.ui.favourites.collectionslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemCollectionBinding
import house.with.swimmingpool.models.ShortCollection
import house.with.swimmingpool.ui.SmallPhotosAdapter

class CollectionsAdapter(
    var items: List<ShortCollection>,
    var onItemSearch: (ShortCollection) -> Unit,
    var onOpenMenu: (ShortCollection) -> Unit,
    var openCatalog: () -> Unit
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

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
            itemView.setOnClickListener { onItemSearch.invoke(item) }
            view.textView8.setOnClickListener { openCatalog.invoke() }
            view.name.text = item.name
            view.counter.text = "${item.photos?.size ?: 0} объектов"
            view.mediaRV.layoutManager =
                GridLayoutManager(itemView.context, 4)
            view.mediaRV.adapter = SmallPhotosAdapter(item.photos?.take(4) ?: listOf())
            view.mediaRV.visibility = if (item.photos.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

}