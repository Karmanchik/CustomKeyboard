package house.with.swimmingpool.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.transaction
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.ActivitySearchBinding
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.search.fragments.SearchTagButtonFragment
import house.with.swimmingpool.ui.search.fragments.SearchesListFragment


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

            inputText.doOnTextChanged { text, start, before, count ->
                Log.e("ttttttt", text.toString())
                if (count != 0) {
                    showCatalogButton.isEnabled = true
                    showCatalogButton.text = "Показать все совпадения (740)"
                } else {
                    showCatalogButton.isEnabled = false
                    showCatalogButton.text = "Введите запрос для поиска"
                }
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

            showCatalogButton.setOnClickListener {
                supportFragmentManager.transaction {
                    replace(R.id.searchTagFrame, SearchesListFragment())
                }
            }
        }
    }

    override fun showInformation(text: String) {
        searchBinding.apply {
            inputText.setText(text)

        }

    }
}