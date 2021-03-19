package house.with.swimmingpool.ui.story

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    }

    override fun onStart() {
        super.onStart()

        val items = story?.items ?: listOf()
        binding.timersRV.apply {
            layoutManager = GridLayoutManager(context, items.size)
            adapter = StoryTimersAdapter(
                items,
                close = { finish() },
                onStoryOpen = { setInfo(it) }
            )
        }
        binding.closeIcon.setOnClickListener { finish() }
    }

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
            container.setOnClickListener {
                val isNotNeedClose = (timersRV.adapter as? StoryTimersAdapter)?.next() ?: true
                if (!isNotNeedClose) {
                    finish()
                }
            }
        }
    }

}