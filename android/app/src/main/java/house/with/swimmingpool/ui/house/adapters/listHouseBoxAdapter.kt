package house.with.swimmingpool.ui.house.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.databinding.ItemListHouseBoxBinding
import house.with.swimmingpool.models.Children

class ListHouseBoxAdapter(
        var ctx: Context,
        var items: List<Children?>?
) : RecyclerView.Adapter<ListHouseBoxAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemListHouseBoxBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items?.size ?: 0

    inner class Holder(private val view: ItemListHouseBoxBinding) :
            RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            view.apply {
                items?.get(position)?.apply {
                    textViewTitle.text = title ?: ""
                    if (price != "") {
                        textViewPrice.text = ((price ?: "") + " руб.")
                    }
                    textViewNumber.text = number
                    if (square != null) {
                        textViewSquare.text = "$square соток"
                    } else {
                        textViewSquare.visibility = View.GONE
                    }

                    if (square_area != null) {
                        textViewSquareArea.text = "$square_area м²"
                    } else {
                        textViewSquareArea.visibility = View.GONE
                    }
//                dividerIndicator.setBackgroundColor(Color.parseColor(items?.get(position)?.status?.color))
                }
            }
        }
    }
}