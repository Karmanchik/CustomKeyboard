package house.with.swimmingpool.ui.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import house.with.swimmingpool.databinding.ItemSearchButtonBinding
import house.with.swimmingpool.ui.search.ISearchView

class SearchTagAdapter(
        var ctx: Context,
        var parentView: ISearchView,
        var items: List<String>
) : RecyclerView.Adapter<SearchTagAdapter.Holder>() {

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
        return Holder(ItemSearchButtonBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemSearchButtonBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.apply {
                itemView.setOnClickListener { parentView.showByAdvantagesTag((position+1).toString(), items[position]) }
                buttonWhite.text = items[position]
            }
        }
    }

}