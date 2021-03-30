package house.with.swimmingpool.ui.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.transaction
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.ActivitySearchBinding
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.onRightDrawableClicked
import house.with.swimmingpool.ui.search.fragments.SearchTagButtonFragment
import house.with.swimmingpool.ui.search.fragments.SearchesListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity(), ISearchView {

    private lateinit var searchBinding: ActivitySearchBinding

    private var filterConfig: JsonObject? = null
    private val filterCategories get() = filterConfig?.entrySet()?.map { Pair(it.key, it.value) }
    private val tagsVariants
        get() = filterCategories
                ?.firstOrNull { it.first == "advantages" }
                ?.second
                ?.let { it.asJsonObject.entrySet().map { Pair(it.key, it.value.asString) }.toMap() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        searchBinding.apply {
            inputText.onRightDrawableClicked {
                it.text.clear()
                it.clearFocus()
            }

            var lastCountInputTextChar = 0

            inputText.setCompoundDrawablesWithIntrinsicBounds(
                    null, null,
                    null, null
            )

            inputText.doOnTextChanged { text, start, before, count ->
                if (count != 0) {
                    showCatalogButton.isEnabled = true
                    showCatalogButton.text = "Ищем..."
                    searchFrame.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE

                    inputText.setCompoundDrawablesWithIntrinsicBounds(
                            null, null,
                            ContextCompat.getDrawable(this@SearchActivity, R.drawable.ic_clear_field)
                            , null
                    )

                } else {
                    showCatalogButton.isEnabled = false
                    showCatalogButton.text = "Введите запрос для поиска"
                    searchFrame.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    showAdvantages()

                    inputText.setCompoundDrawablesWithIntrinsicBounds(
                            null, null,
                            null, null
                    )
                }

                lastCountInputTextChar = count

                Handler().postDelayed({
                    if (lastCountInputTextChar == count && count != 0) {
                        showSearchedList(text.toString())
                    }
                }, 1000)

            }

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    RealtyServiceImpl().getParamsForFilter()?.data?.let {
                        launch(Dispatchers.Main) {
                            filterConfig = it
                            App.setting.filterVariants = it
                            App.setting.filterConfig?.let { showAdvantages() }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("test", "load", e)
                }
            }

            buttonClose.setOnClickListener {
                finish()
            }
        }
    }

    private fun showAdvantages() {
        val lisAdvantages = arrayListOf<String>()

        tagsVariants?.forEach {
            lisAdvantages.add(it.value)
        }

        supportFragmentManager.transaction {
            replace(R.id.searchFrame, SearchTagButtonFragment(
                    this@SearchActivity,
                    lisAdvantages))
        }
    }

    override fun showInformation(text: String) {
        searchBinding.apply {
            inputText.setText(text)

        }
    }

    override fun closeActivity() {
        finish()
    }

    private fun showSearchedList(searchWord: String) {

        searchBinding.apply {

            Log.e("testing", searchWord)
            val filter = FilterObjectsRequest(dir = searchWord)

            RealtyServiceImpl().getObjectsByFilter(filter) { data, e ->

                progressBar.visibility = View.GONE

                searchFrame.visibility = View.VISIBLE

                showCatalogButton.text = "Показать все совпадения (740)"

                if (data != null && inputText.text.toString() != "") {
                    App.setting.filterConfig = filter
                    App.setting.houses = data
                    supportFragmentManager.transaction {
                        replace(R.id.searchFrame, SearchesListFragment(data, this@SearchActivity))
                    }
                }

                searchBinding.showCatalogButton.setOnClickListener {
                    getSharedPreferences(
                            App.HOUSE_WITH_SWIMMING_POOL, Context.MODE_PRIVATE)
                            .edit {
                                putString(App.SEARCH_ACTIVITY, App.SEARCH_ACTIVITY_TO_CATALOG)
                            }
                    finish()
                }
            }
        }
    }
}