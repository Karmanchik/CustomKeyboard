package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCatalogBinding
import house.with.swimmingpool.models.HouseCatalogData

class CatalogAdapter(
        var items: List<HouseCatalogData>,
        var ctx: Context,
        var onItemSelected: (HouseCatalogData) -> Unit

): RecyclerView.Adapter<CatalogAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        Log.e("taf", "onCreateViewHolder")
        return Holder(ItemHouseCatalogBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemHouseCatalogBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(position: Int) {
Log.e("taf", "class Holder")
            view.apply {
                val vp = housesImageContainer
                when {
                    items[position].photos != null -> {
                        vp.adapter = CatalogImageAdapter(items[position].photos!!, ctx)
                    }
                    items[position].icon != null -> {
                        vp.adapter = CatalogImageAdapter(listOf(items[position].icon!!), ctx)
                    }
                    else -> {
                        listOf(R.drawable.placeholder)
                    }
                }

                dotsIndicator.setViewPager2(vp)

                items[position].apply {
                    textViewTitle.text = title
                    textViewDescription.text = description
                    textViewPrice.text = price
                    textViewSquare.text = square.toString()          //fix me!!!
                    textViewSquareArea.text = square_area.toString() //fix me!!!
                }
            }
//            itemView.setOnClickListener { onItemSelected.invoke(items[position]) }

//            if(!items[position].isMortgage){
//                view.textViewMortgage.visibility = View.GONE
//                view.textViewData.visibility = View.VISIBLE
//            }
        }

    }

}