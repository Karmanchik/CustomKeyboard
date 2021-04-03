package house.with.swimmingpool.ui.favourites.collectionslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemCollectionBinding
import house.with.swimmingpool.models.CollectionItem

class CollectionsAdapter(
    var items: List<CollectionItem>,
    var onItemSearch: (CollectionItem) -> Unit,
    var onOpenMenu: (CollectionItem) -> Unit
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCollectionBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(private val view: ItemCollectionBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(item: CollectionItem) {
            view.textView8.setOnClickListener { onItemSearch.invoke(item) }
        }
    }

}