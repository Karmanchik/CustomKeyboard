package house.with.swimmingpool.ui.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import house.with.swimmingpool.R
import house.with.swimmingpool.databinding.FragmentHouseBinding
import house.with.swimmingpool.databinding.FragmentInformationBinding

class InformationFragment(
        private val map : Map<String, String>? = null
        ) : Fragment(){

    private var informationBinding: FragmentInformationBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        informationBinding = FragmentInformationBinding.inflate(layoutInflater)

        return informationBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        informationBinding?.apply {

            map?.entries?.forEachIndexed { index, entry ->
                when (index) {
                    0 -> {
                        key.text = entry.key
                        value.text = entry.value
                    }
                    1 -> {
                        key1.text = entry.key
                        value1.text = entry.value
                    }
                    2 -> {
                        key2.text = entry.key
                        value2.text = entry.value
                    }
                    3 -> {
                        key3.text = entry.key
                        value3.text = entry.value
                    }
                    else -> {
                        key4.text = entry.key
                        value4.text = entry.value
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        informationBinding = null
        super.onDestroy()
    }
}