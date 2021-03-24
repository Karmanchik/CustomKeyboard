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

class WhiteButtonAdapter(
    var ctx: Context,
    var items: List<String>
) : RecyclerView.Adapter<WhiteButtonAdapter.Holder>() {

    private var lastCheckedPosition = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
           recyclerView.layoutManager =
               LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemWhiteButtonBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemWhiteButtonBinding) :
        RecyclerView.ViewHolder(view.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(position: Int) {
            view.apply {
                buttonWhite.text = items[position]

                if (position != lastCheckedPosition) {
                    view.whiteButtonLayout.background = ctx.getDrawable(R.drawable.white_button_with_corner4)
                    view.whiteButtonLayout.elevation = 0F
                }else{
                    view.whiteButtonLayout.background = ctx.getDrawable(R.drawable.white_button_without_corners)
                    view.whiteButtonLayout.elevation = 10F
                }

                buttonWhite.setOnClickListener {

                    if(lastCheckedPosition != position){
                        notifyItemChanged(lastCheckedPosition)
                        lastCheckedPosition = position
                        view.whiteButtonLayout.background = ctx.getDrawable(R.drawable.white_button_without_corners)
                        view.whiteButtonLayout.elevation = 10F
                    }
                }
            }
        }
    }

}