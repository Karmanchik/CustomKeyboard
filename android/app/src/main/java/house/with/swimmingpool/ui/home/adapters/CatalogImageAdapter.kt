package house.with.swimmingpool.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCotalogImageBinding
import house.with.swimmingpool.models.House


class CatalogImageAdapter(
    var items: List<House>,
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

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(position: Int) {
            if(position != items.size){

            }else{
                view.imageView2.apply {
                    setImageDrawable(ctx.getDrawable(R.drawable.home_banner))
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