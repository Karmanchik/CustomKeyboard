package house.with.swimmingpool.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCotalogImageBinding
import house.with.swimmingpool.models.House


class CatalogImageAdapter(
    var items: List<String>,
    val ctx: Context
): RecyclerView.Adapter<CatalogImageAdapter.CatalogImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CatalogImageHolder(
            ItemHouseCotalogImageBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CatalogImageHolder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size+1

    inner class CatalogImageHolder(private val view: ItemHouseCotalogImageBinding): RecyclerView.ViewHolder(
        view.root
    ) {
        fun bind(position: Int) {
            if(position != items.size){
                Glide.with(ctx)
                        .load(items[position])
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(view.imageView2)
            }else{
                view.imageView2.apply {

                    Glide.with(ctx)
                            .load(R.drawable.home_banner)
                            .error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder)
                            .into(view.imageView2)

                    setOnClickListener {
                        val intent =
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+7977999999"))
                        startActivity(ctx, intent, null)
                    }
                }
            }
        }
    }
}