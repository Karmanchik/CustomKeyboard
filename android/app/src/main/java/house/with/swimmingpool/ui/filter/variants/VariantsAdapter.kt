package house.with.swimmingpool.ui.filter.variants

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R

class VariantsAdapter(
        var items: MutableList<Pair<String, Boolean>>,
        var ctx: Context
): RecyclerView.Adapter<VariantsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_variant, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
            holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view) {
        private val switch = view.findViewById<SwitchCompat>(R.id.switch1)

        fun bind(item: Pair<String, Boolean>) {
            switch.text = item.first
            switch.isChecked = item.second
            switch.setOnCheckedChangeListener { _, b ->
                items.replaceAll { if (item == it) Pair(it.first, b) else it }
            }
        }

    }

}