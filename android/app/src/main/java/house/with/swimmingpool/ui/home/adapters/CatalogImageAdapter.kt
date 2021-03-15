package house.with.swimmingpool.ui.home.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ItemHouseCatalogBinding
import house.with.swimmingpool.databinding.ItemHouseCotalogImageBinding
import house.with.swimmingpool.models.House

class CatalogImageAdapter(
        var items: List<House>
): RecyclerView.Adapter<CatalogImageAdapter.CatalogImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CatalogImageHolder(ItemHouseCotalogImageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CatalogImageHolder, position: Int) =
            holder.bind(position)

    override fun getItemCount() = items.size+1

    inner class CatalogImageHolder(private val view: ItemHouseCotalogImageBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            if(position != items.size){

            }else{
//                view.imageView2.setImageDrawable()
            }
        }

    }
}