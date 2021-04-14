package house.with.swimmingpool.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemStatusBinding
import house.with.swimmingpool.databinding.ItemWhiteButtonBinding
import house.with.swimmingpool.ui.house.adapters.WhiteButtonAdapter
import house.with.swimmingpool.ui.house.interfaces.ISingleHouseView

class StatusesAdapter (
    var ctx: Context,
    var items: List<String>
) : RecyclerView.Adapter<StatusesAdapter.Holder>() {

    private var lastCheckedPosition = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemStatusBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemStatusBinding) :
        RecyclerView.ViewHolder(view.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(position: Int) {
            view.apply {
                statusTextView.text =
                    if(position == 0){
                        items[position]
                    }else{
                        "·  ${items[position]}"
                    }
            }
        }
    }
}