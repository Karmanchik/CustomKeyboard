package house.with.swimmingpool.ui.home.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.ui.story.StoryActivity

class StoriesAdapter(
        var items: List<String>
) : RecyclerView.Adapter<StoriesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_stories, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, StoryActivity::class.java))
            }
        }

    }

}