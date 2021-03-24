package house.with.swimmingpool.ui.house.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemWhiteButtonBinding

class MainGalleryAndDateAdapter (
        var ctx: Context,
        var items: List<String>
) : RecyclerView.Adapter<MainGalleryAndDateAdapter.Holder>() {

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

        @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
        fun bind(position: Int) {
            view.apply {
                buttonWhite.text = items[position]

                if (position != lastCheckedPosition) {
                    view.buttonWhite.background = ctx.getDrawable(R.drawable.white_button_with_corner4)
                    view.buttonWhite.setTextColor(Color.parseColor("#000000"))
                }else{
                    view.buttonWhite.background = ctx.getDrawable(R.drawable.blue_button_with_corner4)
                    view.buttonWhite.setTextColor(Color.parseColor("#FFFFFF"))
                }

                buttonWhite.setOnClickListener {

                    if(lastCheckedPosition != position){
                        notifyItemChanged(lastCheckedPosition)
                        lastCheckedPosition = position
                        view.buttonWhite.background = ctx.getDrawable(R.drawable.blue_button_with_corner4)
                        view.buttonWhite.setTextColor(Color.parseColor("#FFFFFF"))
                    }
                }
            }
        }
    }

}