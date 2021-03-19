package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCatalogLastImageCallHolderBinding
import house.with.swimmingpool.databinding.ItemHouseCatalogListVideoBinding
import house.with.swimmingpool.databinding.ItemHouseCotalogImageBinding


class CatalogImageAdapter(
    var items: List<String>,
    var videos: List<String>?,
    val ctx: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemsCount = (videos?.size ?: 0) + items.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0 -> {
                CatalogImageHolder(
                    ItemHouseCotalogImageBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            1 -> {
                CatalogListVideoHolder(
                    ItemHouseCatalogListVideoBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            else -> {
                 CatalogLastImageHolder(
                     ItemHouseCatalogLastImageCallHolderBinding.inflate(
                         layoutInflater,
                         parent,
                         false
                     )
                 )
            }
        }
    }

    override fun getItemViewType(position: Int) : Int{
        return when {
            position < itemsCount - (videos?.size ?: 0) - 1 -> { 0 }
            position != itemsCount - 1 -> { 1 }
            else -> {2}
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CatalogImageHolder -> {
                holder.bind(position)
            }
            is CatalogLastImageHolder -> {
                holder.bind()
            }
            is CatalogListVideoHolder ->{
                holder.bind(position - items.size)
            }
        }
    }


    override fun getItemCount() = (videos?.size ?: 0)  + items.size + 1

    inner class CatalogImageHolder(private val view: ItemHouseCotalogImageBinding): RecyclerView.ViewHolder(
        view.root
    ) {
        fun bind(position: Int) {
            Glide.with(ctx)
                .load(items[position])
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(view.imageView2)
        }
    }

    inner class CatalogLastImageHolder(private val view: ItemHouseCatalogLastImageCallHolderBinding):
        RecyclerView.ViewHolder(view.root) {
        fun bind(){
            view.collLayout.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+7977999999"))
                startActivity(ctx, intent, null)
            }
        }
    }

    inner class CatalogListVideoHolder(private val view: ItemHouseCatalogListVideoBinding):
        RecyclerView.ViewHolder(view.root) {
            fun  bind(position: Int){
                view.youTubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = "-cYOlHknhBU"//videos?.get(position) ?: ""
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }

    }
}