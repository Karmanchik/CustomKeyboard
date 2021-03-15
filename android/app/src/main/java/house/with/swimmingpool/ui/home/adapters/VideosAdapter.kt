package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemVideoBinding
import house.with.swimmingpool.models.Video
import house.with.swimmingpool.models.VideosData

class VideosAdapter(
    val ctx: Context,
    val items: List<VideosData>,
    var onItemSelected: (VideosData) -> Unit
): RecyclerView.Adapter<VideosAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemVideoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = if(items.size > 2) 2 else items.size

    inner class Holder(private val view: ItemVideoBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
//            itemView.setOnClickListener { onItemSelected.invoke(items[position]) }
            Glide.with(ctx)
                .load(items[position].icon)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(view.imageViewVideo)

            view.textViewTitle.text = items[position].title
        }

    }

}