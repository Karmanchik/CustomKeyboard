package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.BannersServiceImpl
import house.with.swimmingpool.databinding.ItemBigBannerBinding
import house.with.swimmingpool.databinding.ItemNewsBinding
import house.with.swimmingpool.databinding.ItemVideoBinding
import house.with.swimmingpool.models.NewsData
import house.with.swimmingpool.models.Video
import house.with.swimmingpool.models.VideosData

class VideosAdapter(
        private val isFull: Boolean = false,
        val ctx: Context,
        val items: List<Any?>,
        var onItemSelected: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val obj = 1
        const val big = 2
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is VideosData -> obj
        else -> {
            big
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            obj ->
                Holder(ItemVideoBinding.inflate(layoutInflater, parent, false))
            else -> BigAd(ItemBigBannerBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is VideosAdapter.Holder -> holder.bind(items[position] as VideosData)
        is VideosAdapter.BigAd -> holder.bind()
        else -> {
        }
    }

    override fun getItemCount() = if (isFull) items.size else if (items.size > 2) 2 else items.size

    inner class Holder(private val view: ItemVideoBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(item: VideosData) {
            itemView.setOnClickListener { onItemSelected.invoke(item.id) }
            Glide.with(ctx)
                    .load(item.icon)
                    .error(R.drawable.error_placeholder_midle)
                    .placeholder(R.drawable.gradient_placeholder_small)
                    .dontAnimate()
                    .into(view.imageViewVideo)

            view.textViewTitle.text = item.title
        }
    }

    inner class BigAd(private val view: ItemBigBannerBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind() {
            BannersServiceImpl().getBanners { data, e ->
                Glide.with(itemView.context)
                        .load(data?.get(0)?.bigBanner)
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .dontAnimate()
                        .into(view.bigAdBanner)
            }
        }
    }
}