package com.greencrop.marketlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mvvmsample.api.ApiClient
import com.google.gson.GsonBuilder
import com.greencrop.R
import com.greencrop.models.getMarkketDataModel
import com.greencrop.singleton.toast
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class VegActivity : AppCompatActivity() {
//    var list: ArrayList<FruistModel> = ArrayList<FruistModel>()
//    var listView: ListView? = null
    var historyArraylist: ArrayList<getMarkketDataModel> = ArrayList<getMarkketDataModel>()
    var listView: ListView? = null
    companion object{
        var images: ArrayList<Int> = ArrayList<Int>()
    }
    var dialog: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veg)


        val cat = intent.getStringExtra("cat")
        val textView = findViewById<TextView>(R.id.txtview)
        textView.text = "$cat Vegitables List"
        listView = findViewById(R.id.listview)
        dialog = findViewById<ProgressBar>(R.id.loader)


        cat?.let { fetchMarketData(this, it,"veg") }


//        if (cat == "Delhi") {
//        addFruits("Potato", "40", R.drawable.mango)
//        addFruits("Tomato", "70", R.drawable.custurd_apple)
//        addFruits("Carrot", "40", R.drawable.water_melon)
//        addFruits("Corn", "50", R.drawable.banana)
//        addFruits("Egg Plant", "120", R.drawable.apple)
    //        addFruits("Onions", "90", R.drawable.grapes)
//        addAdapter()
//        }
    }
    fun fetchMarketData(context: Context, city: String, type:String) {
        var city1= RequestBody.create(MediaType.parse("text/plain"), city)
        var type1= RequestBody.create(MediaType.parse("text/plain"), type)
        ApiClient.apiService.getMarketData(city1,type1)
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
                        dialog?.visibility= View.GONE
                        historyArraylist = response.body()!!
                        val fruitsAdapter = FruitsAdapter(
                            this@VegActivity,
                            R.layout.list_layout,
                            historyArraylist,
                        )
                        Log.e("list size ", historyArraylist.size.toString() + "")
                        listView!!.adapter = fruitsAdapter
                        Log.e("list size ", historyArraylist.size.toString() + "")

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

//    fun addFruits(name: String?, price: String?, image: Int) {
//        val fruitsModel = FruistModel(
//            name,
//            price,
//            image
//        )
//        list.add(fruitsModel)
//    }

//    fun addAdapter() {
//        val fruitsAdapter = FruitsAdapter(
//            this@VegActivity,
//            R.layout.list_layout, list
//        )
//        Log.e("list size ", list.size.toString() + "")
//        listView!!.adapter = fruitsAdapter
//    }
}