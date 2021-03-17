package house.with.swimmingpool.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemStoriesBinding
import house.with.swimmingpool.models.StoriesData
import house.with.swimmingpool.ui.startActivity
import house.with.swimmingpool.ui.story.StoryActivity

class StoriesAdapter(
    var items: List<StoriesData>
) : RecyclerView.Adapter<StoriesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemStoriesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemStoriesBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(item: StoriesData) {
            Glide.with(itemView.context)
                .load(item.icon)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(view.imageViewStories)

            view.textViewTitle.text = item.title
            itemView.setOnClickListener {
                it.context.startActivity<StoryActivity> {
                    putExtra("item", Gson().toJson(item))
                }
            }
        }

    }

}