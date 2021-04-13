package house.with.swimmingpool.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import house.with.swimmingpool.App
import house.with.swimmingpool.MainActivity
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.BannersServiceImpl
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.ItemBigBannerBinding
import house.with.swimmingpool.databinding.ItemHouseCatalogBinding
import house.with.swimmingpool.databinding.ItemSmallBannerBinding
import house.with.swimmingpool.models.HouseCatalogData
import house.with.swimmingpool.ui.cabinet.CabinetFragment
import house.with.swimmingpool.ui.favourites.adapters.TagAdapter

class CatalogAdapter(
        var items: List<Any>,
        var ctx: Context,
        var onItemSelected: (Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val obj = 1
        const val big = 2
        const val small = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            obj ->
                Holder(ItemHouseCatalogBinding.inflate(layoutInflater, parent, false))
            big -> BigAd(ItemBigBannerBinding.inflate(layoutInflater, parent, false))
            else -> SmallAd(ItemSmallBannerBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is Holder -> holder.bind(items[position] as HouseCatalogData)
        is BigAd -> holder.bind()
        is SmallAd -> holder.bind()
        else -> {}
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is HouseCatalogData -> obj
        is String -> {
            if ((items[position] as String) == "big") big else small
        }
        else -> big
    }

    inner class BigAd(private val view: ItemBigBannerBinding): RecyclerView.ViewHolder(view.root) {
        fun bind() {
            BannersServiceImpl().getBanners { data, e ->
                Glide.with(itemView.context)
                    .load(data?.get(0)?.bigBanner)
                    .error(R.drawable.placeholder)
                    .dontAnimate()
                    .placeholder(R.drawable.placeholder)
                    .into(view.bigAdBanner)
            }
        }
    }

    inner class SmallAd(private val view: ItemSmallBannerBinding): RecyclerView.ViewHolder(view.root) {
        fun bind() {
            BannersServiceImpl().getBanners { data, e ->
                Glide.with(itemView.context)
                    .load(data?.get(1)?.smallBanner)
                    .error(R.drawable.placeholder)
                    .dontAnimate()
                    .placeholder(R.drawable.placeholder)
                    .into(view.firstAdBanner)
                Glide.with(itemView.context)
                    .load(data?.get(2)?.smallBanner)
                    .error(R.drawable.placeholder)
                    .dontAnimate()
                    .placeholder(R.drawable.placeholder)
                    .into(view.secondAdBanner)
            }
        }
    }

    inner class Holder(private val view: ItemHouseCatalogBinding): RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: HouseCatalogData) {

            itemView.setOnClickListener { onItemSelected.invoke(item.id) }
            view.apply {
                housesImageContainerLayout.setOnClickListener { onItemSelected.invoke(item.id) }
//                likeView.setImageResource(R.drawable.ic_like_is_favorite_false)

                item.apply {
                    val vp = housesImageContainer
                    Log.e("photos", photos?.size.toString())
                    vp.adapter = when {
                        photos != null && photos.isNotEmpty() -> {
                            Log.e("photos", photos.size.toString())
                            CatalogImageAdapter(photos, video, ctx, onItemSelected,
                                    item.id
                            )
                        }
                        icon != null -> {
                            Log.e("photos", icon.toString())
                            CatalogImageAdapter(listOf(icon), video, ctx, onItemSelected,
                                    item.id
                            )
                        }
                        else -> {
                            CatalogImageAdapter(listOf(""), video, ctx, onItemSelected,
                                    item.id
                            )
                        }
                    }
                    dotsIndicatorCatalogItem.setViewPager2(vp)

                    if (isFavourite == true) {
                        likeView.setImageResource(R.drawable.ic_like_is_favorite_true)
                    }else{
                        likeView.setImageResource(R.drawable.ic_like_is_favorite_false)
                    }
                }

                item.apply {
                    textViewTitle.text = title
                    textViewDescription.text = location
                    textViewPrice.text = price
                    if (square != null && square.isNotEmpty() && square != "0" && square != "0.0") {
                        textViewSquare.text = "$square м²"
                    } else {
                        textViewSquare.visibility = View.GONE
                    }
                    if (square_area != null && square_area.isNotEmpty() && square_area != "0" && square_area != "0.0") {
                        textViewSquareArea.text = "$square_area соток"
                    } else {
                        textViewSquareArea.visibility = View.GONE
                    }

                    if (mainTags != null) {
                        hashTagRV.adapter = TagAdapter(ctx, mainTags)
                    }
                }

                item.apply {
                    likeView.setOnClickListener {
                        if(App.setting.isAuth) {
                            if (isFavourite == true) {
                                likeView.setImageResource(R.drawable.ic_like_is_favorite_false)
                                RealtyServiceImpl().removeFromFavourites(id) { status, e ->
                                    Log.e("removeFromFavourites", "status $status EXCEPTION $e")
                                    isFavourite = false
                                }
                            } else {
                                likeView.setImageResource(R.drawable.ic_like_is_favorite_true)
                                RealtyServiceImpl().addToFavourites(id) { status, e ->
                                    Log.e("addToFavourites", "status $status EXCEPTION $e")
                                    isFavourite = true
                                }
                            }
                        }else{
                           (ctx as MainActivity).showFragment(CabinetFragment())
                        }
                    }
                }

            }
        }
    }

}