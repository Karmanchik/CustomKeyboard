package house.with.swimmingpool.ui.home.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.models.House

class CatalogImageAdapter(
        var items: List<House>
): RecyclerView.Adapter<CatalogImageAdapter.CatalogImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CatalogImageHolder(layoutInflater.inflate(R.layout.item_house_cotalog_image, parent, false))
    }

    override fun onBindViewHolder(holder: CatalogImageHolder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size

    inner class CatalogImageHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
        }

    }
}