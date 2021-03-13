package house.with.swimmingpool.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.models.House

class SeenHousesAdapter(
        var items: List<House>,
        var onItemSelected: (House) -> Unit
): RecyclerView.Adapter<SeenHousesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_house_grid, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            itemView.setOnClickListener { onItemSelected.invoke(items[position]) }
        }

    }

}