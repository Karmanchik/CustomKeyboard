package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseGridBinding
import house.with.swimmingpool.models.House
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter

class SeenHousesAdapter(
        val ctx: Context,
        var items: List<HouseCatalogData>,
        var onItemSelected: (
//                House
        ) -> Unit
): RecyclerView.Adapter<SeenHousesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemHouseGridBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemHouseGridBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            itemView.setOnClickListener { onItemSelected.invoke(
//                    items[position]
            ) }

            view.apply {
                items[position].apply {

                    Glide.with(ctx)
                            .load(
                                when {
                                    icon.isNullOrEmpty() ->{
                                        icon
                                    }
                                    photos.isNullOrEmpty() -> {
                                        photos?.get(0)
                                    }
                                    else -> {
                                        ""
                                    }
                                }
                            )
                            .error(R.drawable.error_placeholder_midle)
                            .placeholder(R.drawable.placeholder)
                            .into(imageViewSeen)
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