package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.BannersServiceImpl
import house.with.swimmingpool.databinding.ItemBigBannerBinding
import house.with.swimmingpool.databinding.ItemNewsBinding
import house.with.swimmingpool.models.NewsData
import house.with.swimmingpool.ui.setBannerClick

class NewsAdapter(
        var items: List<Any?>,
        var ctx: Context,
        var onItemSelected: (NewsData) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val obj = 1
        const val big = 2
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(
                ctx, 2, GridLayoutManager.VERTICAL, false
        )
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is NewsData -> obj
        else -> {
            big
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            obj ->
                Holder(ItemNewsBinding.inflate(layoutInflater, parent, false))
            else -> BigAd(ItemBigBannerBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is NewsAdapter.Holder -> holder.bind(items[position] as NewsData)
        is NewsAdapter.BigAd -> holder.bind()
        else -> {
        }
    }

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(item: NewsData) {
            itemView.setOnClickListener { onItemSelected.invoke(item) }
            Glide.with(itemView.context)
                    .load(item.icon)
                    .error(R.drawable.error_placeholder_big)
                    .placeholder(R.drawable.gradient_placeholder_small)
                    .dontAnimate()
                    .into(view.imageViewNews)

            view.textViewTitle.text = item.title
        }
    }

    inner class BigAd(private val view: ItemBigBannerBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind() {
            BannersServiceImpl().getBanners { data, e ->
                Glide.with(itemView.context)
                        .load(data?.get(0)?.bigBanner)
                        .error(R.drawable.placeholder)
                        .dontAnimate()
                        .placeholder(R.drawable.placeholder)
                        .into(view.bigAdBanner)
            }
            view.bigAdBanner.setBannerClick(view.bigAdBannerLayout, ctx){}
        }
    }
}