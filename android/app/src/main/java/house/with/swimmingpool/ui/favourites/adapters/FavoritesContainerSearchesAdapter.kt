package house.with.swimmingpool.ui.favourites.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.ItemSearchedBinding
import house.with.swimmingpool.models.Search
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.catalog.Label

class FavoritesContainerSearchesAdapter(
        var items: List<Search>,
        val openSearch: (Search) -> Unit,
        val getLabels: (FilterObjectsRequest) -> List<Label>,
        val delete: (Search) -> Unit,
        val push: (Search) -> Unit,
) : RecyclerView.Adapter<FavoritesContainerSearchesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemSearchedBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: ItemSearchedBinding) : RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {

            val startPrice = items[position].config?.price_all_from?.addDividers()
            val endPrice = items[position].config?.price_all_to?.addDividers()

            itemView.setOnClickListener {
                openSearch.invoke(items[position])
            }

            view.textView10.text = items[position].name?: ""

            view.price.text = when{
                startPrice != null && endPrice != null -> "от $startPrice до $endPrice руб."
                startPrice != null -> "от $startPrice руб."
                endPrice != null -> "до $endPrice руб."
                else -> "от до"
            }

            view.TagRV.adapter = SearchTagAdapter(itemView.context,
                    items[position].config?.let { getLabels(it) }?.map { it.title } ?: listOf())
            view.delete.setOnClickListener { delete.invoke(items[position]) }

            val item = items[position]
            view.pushSwitch.setOnClickListener { push.invoke(item) }
            view.pushSwitch.isChecked = item.push ?: false
        }
    }

    private fun String.addDividers(): String {
        return reversed()
                .toList()
                .chunked(3)
                .map { it.joinToString("") }
                .joinToString(" ")
                .reversed()
    }

}