package house.with.swimmingpool.ui.story

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ActivityStoryBinding
import house.with.swimmingpool.databinding.FragmentHomeBinding
import house.with.swimmingpool.models.StoriesData
import house.with.swimmingpool.models.StoriesItem


class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private var story: StoriesData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decorView: View = window.decorView
        val uiOptions: Int = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        val actionBar = actionBar
        actionBar?.hide()

        story = Gson().fromJson(intent.getStringExtra("item"), StoriesData::class.java)
        story?.items?.forEach {
            Glide.with(this)
                .load(it.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .submit()
        }
    }

    override fun onStart() {
        super.onStart()

        val items = story?.items ?: listOf()
        binding.timersRV.apply {
            layoutManager = GridLayoutManager(context, items.size)
            adapter = StoryTimersAdapter(
                items,
                false,
                close = { finish() },
                onStoryOpen = { setInfo(it) }
            )
        }
        binding.closeIcon.setOnClickListener { finish() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setInfo(item: StoriesItem) {
        binding.apply {
            button.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.url)
                startActivity(intent)
            }
            title.text = item.title
            description.text = item.title
            Glide.with(this@StoryActivity)
                .load(item.poster)
                .centerCrop()
                .into(container)

            container.setOnTouchListener { view, event ->
                val adapter = timersRV.adapter as? StoryTimersAdapter

                if (event.action == MotionEvent.ACTION_UP) {
                    adapter?.isStop = false
                    val isBackClick = view.width / 2 > event.x

                    if (!isBackClick) {
                        val isNotNeedClose = adapter?.next() ?: true
                        if (!isNotNeedClose) {
                            finish()
                        }
                    } else {
                        adapter?.previous()
                    }
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    adapter?.isStop = true
                } else {
                    adapter?.isStop = false
                }
                true
            }
        }
    }

}