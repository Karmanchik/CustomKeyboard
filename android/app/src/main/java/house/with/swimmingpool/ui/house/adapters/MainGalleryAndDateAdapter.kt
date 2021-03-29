package house.with.swimmingpool.ui.house.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemWhiteButtonBinding
import house.with.swimmingpool.models.Img
import house.with.swimmingpool.ui.house.interfaces.ISingleHouseView

class MainGalleryAndDateAdapter (
        var ctx: Context,
        var items: List<Pair<String, List<Img>>>?,
        var parentView: ISingleHouseView
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

    override fun getItemCount() = items?.size ?: 0

    inner class Holder(private val view: ItemWhiteButtonBinding) :
            RecyclerView.ViewHolder(view.root) {

        @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
        fun bind(position: Int) {
            view.apply {
                var key = ""
                val listOfImages: ArrayList<String> = arrayListOf()

                items?.get(position)?.let {
                    Log.e("listOfImages", it.first)
                    key = it.first
                    it.second.forEach { im ->
                        listOfImages.add(im.url)
                    }
                }
                buttonWhite.text = key

                if (position != lastCheckedPosition) {
                    view.buttonWhite.background = ctx.getDrawable(R.drawable.white_button_with_corner4)
                    view.buttonWhite.setTextColor(Color.parseColor("#000000"))
                } else {
                    view.buttonWhite.background = ctx.getDrawable(R.drawable.blue_button_with_corner4)
                    view.buttonWhite.setTextColor(Color.parseColor("#FFFFFF"))
                }

                buttonWhite.setOnClickListener {

                    if (lastCheckedPosition != position) {
                        notifyItemChanged(lastCheckedPosition)
                        lastCheckedPosition = position
                        view.buttonWhite.background = ctx.getDrawable(R.drawable.blue_button_with_corner4)
                        view.buttonWhite.setTextColor(Color.parseColor("#FFFFFF"))
                    }
                    parentView.showHeaderGallery(listOfImages)
                }
            }
        }
    }

}