package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemNewsBinding
import house.with.swimmingpool.models.News
import house.with.swimmingpool.models.NewsData

class NewsAdapter(
        var items: List<NewsData>,
        var ctx: Context,
        var onItemSelected: (NewsData) -> Unit
): RecyclerView.Adapter<NewsAdapter.Holder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(
            ctx, 2, GridLayoutManager.VERTICAL, false
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemNewsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = if(items.size < 4) {items.size} else { 4 }

    inner class Holder(private val view: ItemNewsBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
//            itemView.setOnClickListener { onItemSelected.invoke(items[position]) }
            Glide.with(ctx)
                .load(items[position].icon)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(view.imageViewNews)

            view.textViewTitle.text = items[position].title
        }

    }

}