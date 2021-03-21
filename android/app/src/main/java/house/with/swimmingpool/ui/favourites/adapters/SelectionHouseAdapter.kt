package house.with.swimmingpool.ui.favourites.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemSelectionHousesBinding

class SelectionHouseAdapter(
        var ctx : Context,
        var items: List<String>
) : RecyclerView.Adapter<SelectionHouseAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemSelectionHousesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemSelectionHousesBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.photoRV.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = SelectionPhotoAdapter(ctx, listOf("", "", "", ""))
            }
        }
    }
}