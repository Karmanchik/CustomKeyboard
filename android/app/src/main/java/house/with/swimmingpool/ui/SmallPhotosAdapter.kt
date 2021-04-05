package house.with.swimmingpool.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemSmallPhotoBinding

class SmallPhotosAdapter(
    var items: List<String>
) : RecyclerView.Adapter<SmallPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemSmallPhotoBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val view: ItemSmallPhotoBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(item: String) {
            Glide.with(itemView.context)
                .load(item)
                .error(R.drawable.error_placeholder_midle)
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .into(view.photo)
        }
    }

}