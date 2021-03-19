package house.with.swimmingpool.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHeaderHouseBinding
import house.with.swimmingpool.models.MainBannersData

class HeaderAdapter(
        var items: List<MainBannersData>,
        var onItemSelected: () -> Unit
): RecyclerView.Adapter<HeaderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemHeaderHouseBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemHeaderHouseBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.apply {
                items[position].apply {
                    if (banner != null) {
                        Glide.with(itemView.context)
                                .load(items[position].banner)
                                .error(R.drawable.error_placeholder_big)
                                .placeholder(R.drawable.gradient_placeholder_big)
                                .into(imageView3)
                    }
                    textView17.text = name
                    textView21.text = description
                }
            }
            itemView.setOnClickListener { onItemSelected.invoke() }
        }

    }

}