package house.with.swimmingpool.ui.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import house.with.swimmingpool.R

class StoryActivity : AppCompatActivity(R.layout.activity_story) {

    override fun onStart() {
        super.onStart()

        findViewById<RecyclerView>(R.id.timersRV).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = StoryTimersAdapter(listOf("", ""))
        }
    }

}