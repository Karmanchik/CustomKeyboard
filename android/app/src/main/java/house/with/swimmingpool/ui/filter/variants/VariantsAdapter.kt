package house.with.swimmingpool.ui.filter.variants

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R

class VariantsAdapter(
        var items: List<Pair<String, Boolean>>,
        var ctx: Context
): RecyclerView.Adapter<VariantsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_variant, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {

        }

    }

}