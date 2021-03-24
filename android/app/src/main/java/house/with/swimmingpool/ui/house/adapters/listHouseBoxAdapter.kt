package house.with.swimmingpool.ui.house.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemListHouseBoxBinding

class ListHouseBoxAdapter(
        var ctx: Context,
        var items: List<String>
) : RecyclerView.Adapter<ListHouseBoxAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemListHouseBoxBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemListHouseBoxBinding) :
            RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {

        }
    }
}