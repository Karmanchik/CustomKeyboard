package house.with.swimmingpool.ui.search.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemOfSearchesListBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.search.ISearchView

class SearchListItemAdapter(
        var ctx: Context,
        var parentView: ISearchView,
        var items: List<HouseCatalogData>,
) : RecyclerView.Adapter<SearchListItemAdapter.Holder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager =
                LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemOfSearchesListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemOfSearchesListBinding) :
            RecyclerView.ViewHolder(view.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(position: Int) {
            itemView.setOnClickListener {
                App.setting.tmpObj = items[position]
                App.setting.isSearchActivityOpen = true
                parentView.closeActivityByObject()
            }
            view.apply {
                items[position].apply {
                    if (icon != null && icon.isNullOrEmpty()) {
                        Glide.with(ctx)
                                .load(icon)
                                .error(R.drawable.error_placeholder_midl)
                                .placeholder(R.drawable.placeholder)
                                .dontAnimate()
                                .into(view.photo)
                    } else {
                        Glide.with(ctx)
                                .load(photos?.first())
                                .error(R.drawable.error_placeholder_midl)
                                .placeholder(R.drawable.placeholder)
                                .dontAnimate()
                                .into(view.photo)
                    }
                    textViewTitle.text = title
                    textViewLocation.text = location
                    textViewPrice.text = "$price ??????."
                }
            }
        }
    }

}