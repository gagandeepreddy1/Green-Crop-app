package com.greencrop.activities.HomeScreen.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.greencrop.R
import com.greencrop.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingFragment : Fragment() {
    // This string will hold the results
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.veerasubmit.setOnClickListener(View.OnClickListener {
            val editor =
                requireActivity().getSharedPreferences("SharedData", Context.MODE_PRIVATE).edit()

            // When submit button is clicked,
            // Ge the Radio Button which is set
            // If no Radio Button is set, -1 will be returned
            val selectedId = veeraradioGroupColor!!.checkedRadioButtonId
            if (selectedId == -1) {
            } else {
                val radioButtonColor = veeraradioGroupColor
                    .findViewById<View>(selectedId) as RadioButton

                // Now display the value of selected item
                // by the Toast message
                if (radioButtonColor.text.toString().equals("Red", ignoreCase = true)) {
                    editor.putString("color", "Red")
                } else if (radioButtonColor.text.toString().equals("Blue", ignoreCase = true)) {
                    editor.putString("color", "Blue")
                } else if (radioButtonColor.text.toString().equals("Green", ignoreCase = true)) {
                    editor.putString("color", "Green")
                }
            }
            val selectedIdTimeFormat = veeraradioGroupTimeFormate!!.checkedRadioButtonId
            if (selectedIdTimeFormat == -1) {
            } else {
                val radioButtonTimeFormat = veeraradioGroupTimeFormate
                    .findViewById<View>(selectedIdTimeFormat) as RadioButton
                if (radioButtonTimeFormat.text.toString().equals("12 hr", ignoreCase = true)) {
                    editor.putString("TimeFormat", "hh:mm:ss")
                } else if (radioButtonTimeFormat.text.toString()
                        .equals("24 hr", ignoreCase = true)
                ) {
                    editor.putString("TimeFormat", "HH:mm:ss")
                }

                // Now display the value of selected item
                // by the Toast message
            }

            editor.apply()

           it.findNavController().navigate(R.id.nav_home)

        })


        return root
    }
}