package house.with.swimmingpool.ui.story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R

class StoryTimersAdapter(
    var items: List<String>
): RecyclerView.Adapter<StoryTimersAdapter.CatalogImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CatalogImageHolder(layoutInflater.inflate(R.layout.item_story_timer, parent, false))
    }

    override fun onBindViewHolder(holder: CatalogImageHolder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    inner class CatalogImageHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
        }

    }
}