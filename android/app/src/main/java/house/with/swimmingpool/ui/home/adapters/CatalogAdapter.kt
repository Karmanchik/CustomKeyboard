package house.with.swimmingpool.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCatalogBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter

class CatalogAdapter(
        var items: List<HouseCatalogData>,
        var ctx: Context,
        var onItemSelected: (HouseCatalogData) -> Unit

): RecyclerView.Adapter<CatalogAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemHouseCatalogBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemHouseCatalogBinding): RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {

            itemView.setOnClickListener { onItemSelected.invoke(items[position]) }
            view.apply {

                likeView.setImageResource(R.drawable.like_enabled)

                items[position].apply {
                    val vp = housesImageContainer
                    Log.e("photos", photos?.size.toString())
                    vp.adapter = when {
                        photos != null && photos.isNotEmpty() -> {
                            Log.e("photos", photos.size.toString())
                             CatalogImageAdapter(photos, listOf("-cYOlHknhBU") , ctx)
                        }
                        icon != null ->{
                            Log.e("photos", icon.toString())
                            CatalogImageAdapter(listOf(icon), listOf("-cYOlHknhBU") , ctx)
                        }
                        else -> {
                            CatalogImageAdapter(listOf(""), listOf("-cYOlHknhBU") , ctx)
                        }
                    }
                    dotsIndicatorCatalogItem.setViewPager2(vp)
                }

                items[position].apply {
                    textViewTitle.text = title
                    textViewDescription.text = location
                    textViewPrice.text = price
                    if(square != null) {
                        textViewSquare.text = "$square м²"      //fix me!!!
                    }else{
                        textViewSquare.visibility = View.GONE
                    }
                    if(square_area != null) {
                        textViewSquareArea.text = "$square_area соток" //fix me!!!
                    }else{
                        textViewSquareArea.visibility = View.GONE
                    }

                    if (mainTags != null) {
                        hashTagRV.adapter = TagAdapter(ctx, mainTags)
                    }
                }
            }
        }

    }

}