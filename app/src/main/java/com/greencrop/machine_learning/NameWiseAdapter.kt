package com.greencrop.machine_learning

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.greencrop.R
import com.greencrop.models.getMarkketDataModel
import java.lang.Exception

class NameWiseAdapter(ctx: Context, var res: Int,
                      list: List<getMarkketDataModel>,var bitmap: Bitmap) :
    ArrayAdapter<getMarkketDataModel?>(ctx, res, list) {
    var fruitList: List<getMarkketDataModel>
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val l: LayoutInflater
            l = LayoutInflater.from(context)
            v = l.inflate(res, null)
        }
        Log.e("item name ", fruitList.get(position).name.toString())
        val imgFruits = v!!.findViewById<ImageView>(R.id.imgFruits)
        val textView = v!!.findViewById<TextView>(R.id.txtview)
        val txtAddress = v.findViewById<TextView>(R.id.txtAddress)
        textView.setText(fruitList[position].city)
        txtAddress.setText("Price: \u20B9 ${fruitList[position].price} / KG \n" +
                "Fertilizer Name: ${fruitList[position].fertilizer_name}\n"+
                "Fertilizer Cost: \u20B9 ${fruitList[position].fertilizer_cost}\n"+
                "Quantity : ${fruitList[position].quantity_per_hactor}"
        )
        try {
             imgFruits.setImageBitmap(bitmap)
        }catch (e:Exception){}
        return v
    }

    init {
        fruitList = list
    }
}