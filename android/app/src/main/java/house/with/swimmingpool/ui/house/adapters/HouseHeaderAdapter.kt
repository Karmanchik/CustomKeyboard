package house.with.swimmingpool.ui.house.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHeaderHouseSingleImageBinding

class HouseHeaderAdapter(
        var items: List<String>,
) : RecyclerView.Adapter<HouseHeaderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemHeaderHouseSingleImageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemHeaderHouseSingleImageBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            Glide.with(itemView.context)
                    .load(items[position])
                    .error(R.drawable.error_placeholder_big)
                    .placeholder(R.drawable.gradient_placeholder_big)
                    .dontAnimate()
                    .into(view.imageView3)

            Log.e("testing", items[position])
        }
    }
}