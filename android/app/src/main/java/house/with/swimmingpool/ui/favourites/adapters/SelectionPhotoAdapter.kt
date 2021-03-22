package house.with.swimmingpool.ui.favourites.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemSelectionHousePhotoBinding

class SelectionPhotoAdapter (
    var ctx : Context,
    var items: List<String>
    ) : RecyclerView.Adapter<SelectionPhotoAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemSelectionHousePhotoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemSelectionHousePhotoBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
        }
    }
}
