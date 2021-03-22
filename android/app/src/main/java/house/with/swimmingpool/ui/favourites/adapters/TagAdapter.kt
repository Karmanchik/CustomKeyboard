package house.with.swimmingpool.ui.favourites.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import house.with.swimmingpool.databinding.ItemTegCatalogBinding
import house.with.swimmingpool.models.MainTags


class TagAdapter(
        var ctx: Context,
        var items: List<MainTags>,
) : RecyclerView.Adapter<TagAdapter.Holder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager =
            FlexboxLayoutManager(ctx)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START
        recyclerView.layoutManager = layoutManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemTegCatalogBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemTegCatalogBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.apply {
                textViewTag.text = items[position].text
                textViewTag.setBackgroundColor(Color.parseColor(items[position].color))
            }
        }
    }

}