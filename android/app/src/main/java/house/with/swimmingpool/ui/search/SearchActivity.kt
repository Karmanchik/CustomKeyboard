package house.with.swimmingpool.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.transaction
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ActivitySearchBinding
import house.with.swimmingpool.ui.house.InformationFragment
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.search.fragments.SearchTagButtonFragment


class SearchActivity : AppCompatActivity(), ISearchView {

    private lateinit var searchBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        searchBinding.apply {
            inputText.onRightDrawableClicked {
                it.text.clear()
                it.clearFocus()
            }



            supportFragmentManager.transaction {
                replace(R.id.searchTagFrame, SearchTagButtonFragment(
                    this@SearchActivity,
                    listOf(
                        "Коттеджи в сочи",
                        "Дом с бассейном",
                        "У моря",
                        "Красная поляна",
                        "На горе"
                    )))
            }
        }
    }

    override fun showInformation() {
        TODO("Not yet implemented")
    }
}