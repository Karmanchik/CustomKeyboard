package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentFavouritesContainerHousesBinding
import house.with.swimmingpool.databinding.ItemSelectionHousesBinding
import house.with.swimmingpool.databinding.ItemStoriesBinding
import house.with.swimmingpool.models.StoriesData
import house.with.swimmingpool.ui.story.StoryActivity

class SelectionHouseAdapter(
        var ctx : Context,
        var items: List<String>
) : RecyclerView.Adapter<SelectionHouseAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemSelectionHousesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemSelectionHousesBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.photoRV.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = SelectionPhotoAdapter(ctx, listOf("", "", "", ""))
            }
        }
    }
}