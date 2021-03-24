package house.with.swimmingpool.ui.house.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import house.with.swimmingpool.databinding.ItemAdvantagesBinding
import house.with.swimmingpool.models.MainTags

class AdvantagesAdapter(
        var ctx: Context,
        var items: List<String>,
) : RecyclerView.Adapter<AdvantagesAdapter.Holder>() {

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
        return Holder(ItemAdvantagesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemAdvantagesBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.apply {
                buttonWhite.text = items[position]
            }
        }
    }

}