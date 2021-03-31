package house.with.swimmingpool.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemTagNewsBinding

class NewsTagAdapter (
        var ctx: Context,
        var items: List<String>?,
) : RecyclerView.Adapter<NewsTagAdapter.Holder>() {


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager =
                LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemTagNewsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items?.size ?: 0

    inner class Holder(private val view: ItemTagNewsBinding) :
            RecyclerView.ViewHolder(view.root) {
        fun bind(position: Int) {

            view.tagTextView.text = items?.get(position) ?: ""

        }
    }
}