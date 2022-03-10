package com.greencrop.machine_learning

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
import android.graphics.Bitmap




class GetNameWiseMLActivity : AppCompatActivity() {
    var mlArraylist: ArrayList<getMarkketDataModel> = ArrayList<getMarkketDataModel>()
    var listView: ListView? = null
    var bitmap: Bitmap? = null
    var dialog: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_name_wise_mlactivity)
        dialog = findViewById<ProgressBar>(R.id.loader)

        val name = intent.getStringExtra("name")
         bitmap = intent.getParcelableExtra("BitmapImage") as Bitmap?
        val textView = findViewById<TextView>(R.id.txtview)
        textView.text = "$name"
        listView = findViewById(R.id.listview)
        name?.let { fetchMarketData(this, it) }
    }

    fun fetchMarketData(context: Context, name: String) {
        var name1= RequestBody.create(MediaType.parse("text/plain"), name)
        ApiClient.apiService.getMLData(name1)
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
                        mlArraylist = response.body()!!
                        val namewise = NameWiseAdapter(
                            this@GetNameWiseMLActivity,
                            R.layout.list_layout_ml,
                            mlArraylist,bitmap!!
                        )
                        Log.e("list size ", mlArraylist.size.toString() + "")
                        listView!!.adapter = namewise
                        Log.e("list size ", mlArraylist.size.toString() + "")

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