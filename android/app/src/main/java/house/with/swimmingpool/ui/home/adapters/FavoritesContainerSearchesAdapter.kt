package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemSearchedBinding
import house.with.swimmingpool.models.HouseOptions

class FavoritesContainerSearchesAdapter(
    var ctx : Context,
    var items: List<HouseOptions>
) : RecyclerView.Adapter<FavoritesContainerSearchesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemSearchedBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemSearchedBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.TagRV.adapter = SearchTagAdapter(ctx, items[position].options)
        }
    }

}