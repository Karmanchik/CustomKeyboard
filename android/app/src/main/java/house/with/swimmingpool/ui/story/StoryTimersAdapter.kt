package house.with.swimmingpool.ui.story

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R
import house.with.swimmingpool.models.StoriesItem
import kotlinx.coroutines.*

class StoryTimersAdapter(
    var items: List<StoriesItem>,
    var isStop: Boolean,
    val close: () -> Unit,
    val onStoryOpen: (StoriesItem) -> Unit
) : RecyclerView.Adapter<StoryTimersAdapter.CatalogImageHolder>() {

    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CatalogImageHolder(layoutInflater.inflate(R.layout.item_story_timer, parent, false))
    }

    fun next(): Boolean {
        if (currentPosition == items.lastIndex)
            return false

        currentPosition++
        notifyDataSetChanged()
        return true
    }

    fun previous() {
        if (currentPosition != 0)
            currentPosition--
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CatalogImageHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class CatalogImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val progressBar = view as ProgressBar


        fun bind(item: StoriesItem) {
            progressBar.max = 100

            progressBar.progress = if (adapterPosition >= currentPosition) {
                0
            } else {
                100
            }

            if (adapterPosition == currentPosition) {
                onStoryOpen.invoke(item)
                var isClose = false
                GlobalScope.launch {
                    (0 until 10000).forEach {
                        delay(50)
                        if (!isStop) {
                            launch(Dispatchers.Main) {
                                if (currentPosition == adapterPosition && progressBar.progress in (it - 1..it + 1)) {
                                    progressBar.progress = it + 1
                                    if (progressBar.progress == progressBar.max) {
                                        if (currentPosition == items.lastIndex) {
                                            if (!isClose) {
                                                isClose = true
                                                close.invoke()
                                                return@launch
                                            }
                                        } else {
                                            currentPosition = adapterPosition + 1
                                            notifyDataSetChanged()
                                        }
                                    }
                                }
                            }
                        } else {
                            while (isStop) {
                                Log.e("test isstop", isStop.toString())
                                delay(100)
                            }
                        }
                    }
                }
            }
        }

    }
}