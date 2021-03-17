package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
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
