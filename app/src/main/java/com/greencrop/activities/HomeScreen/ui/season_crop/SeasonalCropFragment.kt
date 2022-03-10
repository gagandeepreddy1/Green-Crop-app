package com.greencrop.activities.HomeScreen.ui.season_crop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.greencrop.activities.HomeScreen.HomeActivity
import com.greencrop.activities.HomeScreen.ui.fertilizer_for_crop.FertiliserForCropViewModel
import com.greencrop.databinding.FragmentFertilizerShopBinding

class SeasonalCropFragment : Fragment() {
    private lateinit var fertilizerShopViewModel: FertiliserForCropViewModel
    private var _binding: FragmentFertilizerShopBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!    // This string will hold the results
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFertilizerShopBinding.inflate(inflater, container, false)

        fertilizerShopViewModel =
            ViewModelProvider(this).get(FertiliserForCropViewModel::class.java)
        fertilizerShopViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        val root: View = binding.root
        if (HomeActivity.season.equals("rabi"))
            binding.txtFertilizer.text = "1. Mango\n" +
                    "2. Apple \n"
        if (HomeActivity.season.equals("kharif"))
            binding.txtFertilizer.text = "1. grapes\n" +
                    "2. Apple2 \n"
        if (HomeActivity.season.equals("zaid"))
            binding.txtFertilizer.text = "1. potato\n" +
                    "3. Apple3 \n"

        return root
    }
}