package house.with.swimmingpool.ui.story

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R


class StoryActivity : AppCompatActivity(R.layout.activity_story) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView: View = window.decorView
        val uiOptions: Int = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        val actionBar = actionBar
        actionBar?.hide()
    }

    override fun onStart() {
        super.onStart()

        val count = 6

        findViewById<RecyclerView>(R.id.timersRV).apply {
            layoutManager = GridLayoutManager(context, count)
            adapter = StoryTimersAdapter(listOf("", ""))
        }
    }

}