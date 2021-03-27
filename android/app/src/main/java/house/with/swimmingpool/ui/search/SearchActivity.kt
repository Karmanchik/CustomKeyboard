package house.with.swimmingpool.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ActivityMainBinding
import house.with.swimmingpool.databinding.ActivitySearchBinding
import house.with.swimmingpool.ui.onRightDrawableClicked

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        searchBinding.inputText.onRightDrawableClicked {
            it.text.clear()
            it.clearFocus()
        }


    }
}