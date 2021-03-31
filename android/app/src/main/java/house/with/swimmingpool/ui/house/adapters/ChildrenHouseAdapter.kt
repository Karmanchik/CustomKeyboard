package house.with.swimmingpool.ui.house.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseGridBinding
import house.with.swimmingpool.models.Children
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.models.HouseExampleData

class ChildrenHouseAdapter (
        val ctx: Context,
        var items: List<HouseCatalogData?>,
        var onItemSelected: (Int) -> Unit
): RecyclerView.Adapter<ChildrenHouseAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemHouseGridBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = if (items.size > 4) 4 else items.size

    inner class Holder(private val view: ItemHouseGridBinding) : RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            itemView.setOnClickListener { onItemSelected.invoke(items[position]?.id ?: 0) }

            view.apply {

                if (items[position] != null) {
                    Glide.with(ctx)
                            .load(
                                    when {
                                         items[position]?.icon != "" -> {
                                            items[position]?.icon
                                        }
                                        items[position]?.photos?.get(0) != "" -> {
                                            items[position]?.photos?.get(0)
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
                items[position]?.apply {
                    textViewTitle.text = title
//                    textViewLocation.text = location

                    textViewPrice.text = "$price руб."
                    if (this.square != null && square != "0") {
                        textViewSquare.text = "$square м²"      //fix me!!!
                    } else {
                        textViewSquare.visibility = View.GONE
                    }

                    Log.e("testinggg", square_area.toString())

                    if (square_area != null && square_area != "0") {
                        textViewSquareArea.text = "$square_area соток" //fix me!!!
                    } else {
                        textViewSquareArea.visibility = View.GONE
                    }
                }
            }
        }
    }
}