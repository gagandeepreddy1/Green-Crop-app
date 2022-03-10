package com.greencrop.activities.HomeScreen.ui.fertilizer_for_crop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.greencrop.R
import com.greencrop.databinding.FragmentFertilizerShopBinding
import java.util.ArrayList
import android.widget.*
import com.example.mvvmsample.api.ApiClient
import com.google.gson.GsonBuilder
import com.greencrop.databinding.FragmentFertilizerForCropsBinding
import com.greencrop.marketlist.FruitsAdapter
import com.greencrop.models.getMarkketDataModel
import com.greencrop.singleton.toast
import kotlinx.android.synthetic.main.fragment_fertilizer_for_crops.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FertiliserForCrop : Fragment() {
    private lateinit var fertiliserForCropViewModel: FertiliserForCropViewModel
    private var _binding: FragmentFertilizerForCropsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!
    var names: ArrayList<String> = ArrayList<String>()

    // This string will hold the results
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFertilizerForCropsBinding.inflate(inflater, container, false)

        fertiliserForCropViewModel =
            ViewModelProvider(this).get(FertiliserForCropViewModel::class.java)
        fertiliserForCropViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        val root: View = binding.root
        var spinnerType = binding.spinnerType
        var spinnerCategory = binding.spinnerCategory
        dialog = binding.loader

        //fetchMarketData(requireContext(), "Delhi", "fruits")

        spinnerType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position == 1)
                    fetchMarketData(requireContext(), "Delhi", "veg")
                else
                    fetchMarketData(requireContext(), "Delhi", "fruits")

            }

        }

        spinnerCategory?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                try {
                    txtFertilizer.text =
                        "Fertilizer Name: ${categoryList.get(position).fertilizer_name}\n" +
                                "Fertilizer Cost: ${categoryList.get(position).fertilizer_cost}\n" +
                                "Quantity Required For Hactare Land: ${categoryList.get(position).quantity_per_hactor}"
                } catch (e: Exception) {
                }
            }

        }


        return root
    }

    var categoryList: ArrayList<getMarkketDataModel> = ArrayList<getMarkketDataModel>()
    var dialog: ProgressBar? = null
    fun fetchMarketData(context: Context, city: String, type: String) {
        names.clear()
        var city1 = RequestBody.create(MediaType.parse("text/plain"), city)
        var type1 = RequestBody.create(MediaType.parse("text/plain"), type)
        ApiClient.apiService.getMarketData(city1, type1)
            ?.enqueue(object : Callback<ArrayList<getMarkketDataModel>> {
                override fun onResponse(
                    call: Call<ArrayList<getMarkketDataModel>>,
                    response: Response<ArrayList<getMarkketDataModel>>,
                ) {
                    //   hideProgressBar()
                    if (response.isSuccessful) {
                        context.toast("Success")
                        Log.e("response is ",
                            GsonBuilder().setPrettyPrinting().create().toJson(response))
                        dialog?.visibility = View.GONE
                        spinnerCategory.visibility = View.VISIBLE
                        categoryList = response.body()!!
                        for (i in 0..categoryList.size - 1) {
                            categoryList.get(i).name?.let { names.add(it) }
                            if (names.get(i).equals("Mango", true)) {
                                txtFertilizer.text =
                                    "Fertilizer Name: ${categoryList.get(i).fertilizer_name}\n" +
                                            "Fertilizer Cost: ${categoryList.get(i).fertilizer_cost}\n" +
                                            "Quantity Required For Hactare Land: ${
                                                categoryList.get(i).quantity_per_hactor
                                            }"
                            }
                            if (names.get(i).equals("Potato", true)) {
                                txtFertilizer.text =
                                    "Fertilizer Name: ${categoryList.get(i).fertilizer_name}\n" +
                                            "Fertilizer Cost: ${categoryList.get(i).fertilizer_cost}\n" +
                                            "Quantity Required For Hactare Land: ${
                                                categoryList.get(i).quantity_per_hactor
                                            }"

                            }
                            if (i == categoryList.size - 1) {
                                val adapter = ArrayAdapter(requireContext(),
                                    android.R.layout.simple_spinner_item, names)
                                spinnerCategory.adapter = adapter
                            }
                        }

                        Log.e("list size ", categoryList.size.toString() + "")
                        Log.e("categoryList ", categoryList.toString() + "")

                    } else {
                        context.toast("something went wrong")
                    }
                }

                override fun onFailure(call: Call<ArrayList<getMarkketDataModel>>, t: Throwable) {
                    //  hideProgressBar()
                    context.toast("onfailure" + t.localizedMessage)
                    Log.e("error onfailure ", t.localizedMessage)
                }
            })

    }

}