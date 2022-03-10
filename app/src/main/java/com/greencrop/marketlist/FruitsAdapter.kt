package com.greencrop.marketlist

import android.app.AlertDialog
import android.content.Context
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

class FruitsAdapter(ctx: Context, var res: Int,
                    list: List<getMarkketDataModel>) :
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
        textView.setText(fruitList[position].name)
        txtAddress.setText("Price: ₹ ${fruitList[position].price} / KG ")
        try {
            MarketActivity.images.get(position)?.let { imgFruits.setBackgroundResource(it) }
        }catch (e:Exception){}
        v.setOnClickListener{
           // Log.e("item name ", fruitList.get(position).name.toString())
            AlertDialog.Builder(context)
                .setTitle("Cost of ${fruitList[position].name}")
                .setMessage("Cost of Item \n" +
                        "Per one KG:- ${fruitList[position].price} Rs.\n" +
                        "Per one Quintal:- ${fruitList[position].price} Rs.\n\n"
                    +"Cost as Per Government KG:- ${fruitList[position].price} Rs.\n"+
                    "Per one KG:- ${fruitList[position].price} Rs.\n" +
                            "Per one Quintal:- ${fruitList[position].price} Rs.\n\n"
                )
                .setPositiveButton("ok") { dialog, which ->
                    //  startActivity(new Intent(HomeActivity.this,LoginRegisterActivity.class));
                }.show()

        }
        return v
    }

    init {
        fruitList = list
    }
}