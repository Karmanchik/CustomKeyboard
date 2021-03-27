package house.with.swimmingpool.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemFilterCatalogBinding

class FilterItemsAdapter(
    var items: List<String>,
    var onItemDeleted: (String) -> Unit
): RecyclerView.Adapter<FilterItemsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemFilterCatalogBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemFilterCatalogBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.title.text = items[position]
            itemView.setOnClickListener {
                onItemDeleted.invoke(items[position])
                notifyItemRemoved(adapterPosition)
            }
        }

    }

}