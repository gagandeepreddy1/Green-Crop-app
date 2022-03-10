package com.greencrop.activities.HomeScreen.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.greencrop.activities.HomeScreen.HomeActivity.Companion.lang
import com.greencrop.activities.HomeScreen.HomeActivity.Companion.lat
import com.greencrop.activities.HomeScreen.ui.fertilizer_for_crop.FertiliserForCropViewModel
import com.greencrop.databinding.FragmentWeatherBinding
import org.json.JSONObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class WeatherFragment : Fragment() {
    private lateinit var slideshowViewModel: FertiliserForCropViewModel
    private var _binding: FragmentWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!    // This string will hold the results
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        slideshowViewModel =
            ViewModelProvider(this).get(FertiliserForCropViewModel::class.java)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        val root: View = binding.root

        val txtCity: TextView = binding.txtCity
        val txtWeatherDesc: TextView = binding.weatherDesc
        val txtTemp: TextView = binding.temp
        val txtDetails: TextView = binding.details
        Log.e("lat", "lat $lat")
        Log.e("lang", "lang $lang")
        var url =
            "http://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lang&APPID=dc2522c3426d32f53ae6e86ea87134d1&units=metric"
        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder()
            .url(url)
            .method("GET", null)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity!!.runOnUiThread { }
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                } else {
                    val responseData = response.body()?.string()
                    var json = JSONObject(responseData)

                    var lon = json.getJSONObject("coord").getString("lon")
                    activity!!.runOnUiThread {
                        var temp = "${json.getJSONObject("main").getString("temp")}"
                       // var sea_level = "Sea Level ${json.getJSONObject("main").getString("sea_level")}"
                        var lat = "Latitude : ${json.getJSONObject("coord").getString("lat")}"
                        var lang = "Longitude ${json.getJSONObject("coord").getString("lon")}"
                        var humidity = "Humidity :${json.getJSONObject("main").getString("humidity")}"
                        var city = "${json.getString("name")}"
                        var country = "${json.getJSONObject("sys").getString("country")}"
                        var wind = "Wind Speed : ${json.getJSONObject("wind").getString("speed")}"
                        var weatherDesc = "${json.getJSONArray("weather").getJSONObject(0).getString("description")}"
                        Log.e("json data ", json.toString())

                        txtCity.text = "$city , $country "
                        txtWeatherDesc.text = weatherDesc
                        txtTemp.text=temp+" \u2103"
                        txtDetails.text="$lat\n$lang\n$humidity\n$wind"

                    }
                }
            }
        })


        return root
    }


}