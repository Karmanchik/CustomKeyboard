package house.with.swimmingpool.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemHouseCatalogBinding
import house.with.swimmingpool.models.House

class CatalogAdapter(
        var ctx: Context,
        var items: List<House>,
        var onItemSelected: (House) -> Unit

): RecyclerView.Adapter<CatalogAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemHouseCatalogBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemHouseCatalogBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(position: Int) {

            val vp = view.housesImageContainer
            vp.adapter = CatalogImageAdapter(listOf(House(), House(), House()), ctx)

            view.dotsIndicator.setViewPager2(vp)

            itemView.setOnClickListener { onItemSelected.invoke(items[position]) }

            if(!items[position].isMortgage){
                view.textViewMortgage.visibility = View.GONE
                view.textViewData.visibility = View.VISIBLE
            }
        }

    }

}