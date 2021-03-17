package house.with.swimmingpool.ui.home.adapters

import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.ui.story.StoryActivity
import house.with.swimmingpool.databinding.ItemStoriesBinding
import house.with.swimmingpool.models.StoriesData

class StoriesAdapter(
        var ctx : Context,
        var items: List<StoriesData>
) : RecyclerView.Adapter<StoriesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemStoriesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemStoriesBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            if(items[position].icon != null){
                Glide.with(ctx)
                    .load(items[position].icon)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(view.imageViewStories)
            }
            view.textViewTitle.text = items[position].title
            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, StoryActivity::class.java).apply {
                    putExtra("item", Gson().toJson())
                })
            }
        }

    }

}