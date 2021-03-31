package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCatalogLastImageCallHolderBinding
import house.with.swimmingpool.databinding.ItemHouseCatalogListVideoBinding
import house.with.swimmingpool.databinding.ItemHouseCotalogImageBinding

class MediaAdapter(
        var items: List<String>?,
       var videos: List<String>?,
       val ctx: Context,
//       var onItemSelected: (Int) -> Unit,
//       var id: Int
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemsCount = (videos?.size ?: 0) + (items?.size ?: 0)

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
            else -> {
                CatalogListVideoHolder(
                        ItemHouseCatalogListVideoBinding.inflate(
                                layoutInflater,
                                parent,
                                false
                        )
                )
            }
//            else -> {
//                CatalogLastImageHolder(
//                        ItemHouseCatalogLastImageCallHolderBinding.inflate(
//                                layoutInflater,
//                                parent,
//                                false
//                        )
//                )
//            }
        }
    }

    override fun getItemViewType(position: Int) : Int{
        return when {
            position < (itemsCount - (videos?.size ?: 0)) -> { 0 }
            else -> { 1 }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CatalogImageHolder -> {
                holder.bind(position)
            }
//            is CatalogLastImageHolder -> {
//                holder.bind()
//            }
            is CatalogListVideoHolder ->{
                holder.bind(position - (items?.size ?: 0))
            }
        }
    }


    override fun getItemCount() =
//            itemsCount
    (videos?.size ?: 0)  + (items?.size ?: 0)

    inner class CatalogImageHolder(private val view: ItemHouseCotalogImageBinding):
            RecyclerView.ViewHolder(view.root) {
        fun bind(position: Int) {
//            itemView.setOnClickListener { onItemSelected.invoke(id) }
            Glide.with(ctx)
                    .load(items?.get(position))
                    .error(R.drawable.error_placeholder_midle)
                    .placeholder(R.drawable.placeholder)
                    .into(view.imageView2)
        }
    }

//    inner class CatalogLastImageHolder(private val view: ItemHouseCatalogLastImageCallHolderBinding):
//            RecyclerView.ViewHolder(view.root) {
//        fun bind(){
//            view.collLayout.setOnClickListener {
//                val intent =
//                        Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+7977999999"))
//                ContextCompat.startActivity(ctx, intent, null)
//            }
//        }
//    }

    inner class CatalogListVideoHolder(private val view: ItemHouseCatalogListVideoBinding):
            RecyclerView.ViewHolder(view.root) {
        fun bind(position: Int) {
            Glide.with(ctx)
//                    .load("https://i.ytimg.com/vi/${videos?.get(position)}/maxresdefault.jpg")
                    .load("https://i.ytimg.com/vi/-cYOlHknhBU/maxresdefault.jpg")
                    .error(R.drawable.error_placeholder_midle)
                    .placeholder(R.drawable.placeholder)
                    .into(view.imageViewVideoPreloader)

            view.youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    val videoId = "-cYOlHknhBU"//videos?.get(adapterPosition - items.size) ?: ""
                    youTubePlayer.loadVideo(videoId, 0f)
                    youTubePlayer.pause()

                    view.imageViewVideoPreloader.setOnClickListener {
                        it.visibility = View.GONE
                        view.relativeLayout.visibility = View.GONE
                        view.youTubePlayerView.visibility = View.VISIBLE
                        youTubePlayer.play()
                        view.youTubePlayerView.enterFullScreen()
                        view.youTubePlayerView.exitFullScreen()
                    }
                }
            })
        }
    }
}