package house.with.swimmingpool.ui.house.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemWhiteButtonBinding
import house.with.swimmingpool.models.Img
import house.with.swimmingpool.ui.house.interfaces.ISingleHouseView

class MainGalleryAndDateAdapter(
    var items: List<Pair<String, List<Img>>>,
    var parentView: ISingleHouseView
) : RecyclerView.Adapter<MainGalleryAndDateAdapter.Holder>() {

    private var lastCheckedPosition = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemWhiteButtonBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemWhiteButtonBinding) :
            RecyclerView.ViewHolder(view.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Pair<String, List<Img>>) {
            view.buttonWhite.apply {
                text = item.first

                if (adapterPosition != lastCheckedPosition) {
                    background = context.getDrawable(R.drawable.white_button_with_corner4)
                    setTextColor(Color.parseColor("#000000"))
                } else {
                    background = context.getDrawable(R.drawable.blue_button_with_corner4)
                    setTextColor(Color.parseColor("#FFFFFF"))
                }

                setOnClickListener {
                    if (lastCheckedPosition != adapterPosition) {
                        notifyItemChanged(lastCheckedPosition)
                        lastCheckedPosition = adapterPosition
                        background = context.getDrawable(R.drawable.blue_button_with_corner4)
                        setTextColor(Color.parseColor("#FFFFFF"))
                    }
                    parentView.showHeaderGallery(ArrayList(item.second.map { it.url }))
                }
            }
        }
    }

}