package house.with.swimmingpool.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.gson.JsonObject
import house.with.swimmingpool.App
import house.with.swimmingpool.R
import house.with.swimmingpool.api.config.controllers.RealtyServiceImpl
import house.with.swimmingpool.databinding.ActivitySearchBinding
import house.with.swimmingpool.models.request.FilterObjectsRequest
import house.with.swimmingpool.ui.home.HomeFragment
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

        overridePendingTransition(
                R.anim.search_enter_animation,
                R.anim.nav_default_exit_anim
        )

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
                    showCatalogButton.text = "????????..."
                    searchFrame.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE

                    SearchesListFragment.singleton?.deleteTag()

                    inputText.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(this@SearchActivity, R.drawable.ic_clear_field),
                        null
                    )

                } else {
                    showCatalogButton.isEnabled = false
                    showCatalogButton.text = "?????????????? ???????????? ?????? ????????????"
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
                        showSearchedList(
                                FilterObjectsRequest(search = text.toString())
                        )
                    }
                }, 300)

            }

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    RealtyServiceImpl().getParamsForFilter()?.data?.let {
                        launch(Dispatchers.Main) {
                            filterConfig = it
                            App.setting.filterVariants = it
                            App.setting.filterConfig?.let { showAdvantages() }
                            showAdvantages()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("test", "load", e)
                }
            }

            buttonClose.setOnClickListener {
                App.setting.isSearchActivityOpen = false
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

    override fun showAdvantages() {
        val lisAdvantages = arrayListOf<String>()

        tagsVariants?.forEach {
            lisAdvantages.add(it.value)
        }

        supportFragmentManager.beginTransaction()
            .replace(
                searchBinding.searchFrame.id, SearchTagButtonFragment(this, lisAdvantages)
            )
            .commit()

        searchBinding.apply {
            showCatalogButton.isEnabled = false
            showCatalogButton.text = "?????????????? ???????????? ?????? ????????????"
        }
    }

    override fun showByAdvantagesTag(text: String, tag: String) {
        searchBinding.apply {
            showCatalogButton.isEnabled = true
            showCatalogButton.text = "????????..."
            searchFrame.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            showSearchedList(FilterObjectsRequest(advantages = listOf(text)), tag)
        }
    }



//    override fun closeActivity() {
////        setResult(RESULT_OK, Intent().putExtra("action", HomeFragment.NAVIGATE_TO_OBJECT))
//        finish()
//    }

    override fun closeActivityByObject() {
        setResult(RESULT_OK, Intent().putExtra("action", HomeFragment.NAVIGATE_TO_OBJECT))
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun showSearchedList(filter : FilterObjectsRequest, tag: String? = null) {

        searchBinding.apply {
            RealtyServiceImpl().getObjectsByFilter(filter) { data, e ->
                try {
                    progressBar.visibility = View.GONE

                    searchFrame.visibility = View.VISIBLE

                    if (data.isNullOrEmpty()) {
                        showCatalogButton.text = "???????????? ???? ??????????????"
                        showCatalogButton.isEnabled = false
                    } else {
                        showCatalogButton.text = "???????????????? ?????? ???????????????????? (${data.size})"
                        showCatalogButton.isEnabled = true
                    }


                    if (data != null && (inputText.text.toString() != "" || filter.advantages != listOf(""))) {
                        App.setting.filterConfig = filter
                        App.setting.houses = data

                        supportFragmentManager.beginTransaction()
                                .replace(
                                        searchBinding.searchFrame.id,
                                        SearchesListFragment(data, this@SearchActivity, tag)
                                )
                                .commit()
                    }

                    searchBinding.showCatalogButton.setOnClickListener {
                        App.setting.isSearchActivityOpen = true
                        setResult(
                                RESULT_OK,
                                Intent().putExtra("action", HomeFragment.NAVIGATE_TO_CATALOG)
                        )
                        finish()
                    }
                } catch (e: Exception) {
                }
            }
        }
    }
}