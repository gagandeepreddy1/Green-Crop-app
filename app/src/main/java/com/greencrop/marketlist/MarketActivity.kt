package com.greencrop.marketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greencrop.R
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_market.*
import java.util.ArrayList

class MarketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)
        val cat = intent.getStringExtra("cat")
        txtview.text="$cat Market List"
        btnDelhi.setOnClickListener{
            openSubCategory(btnDelhi.text.toString())
        }
        btnMumbai.setOnClickListener{
            openSubCategory(btnMumbai.text.toString())
        }
        btnChennai.setOnClickListener{
            openSubCategory(btnChennai.text.toString())
        }
        btnHydrabad.setOnClickListener{
            openSubCategory(btnHydrabad.text.toString())
        }
        btnVijayvada.setOnClickListener{
            openSubCategory(btnVijayvada.text.toString())
        }
    }
    companion object{
        var images: ArrayList<Int> = ArrayList<Int>()
    }
    private fun openSubCategory(category: String?) {
        val cat = intent.getStringExtra("cat")
        if (cat.equals("Fruits")) {
            images.clear()
            images.add(R.drawable.mango)
            images.add(R.drawable.custurd_apple)
            images.add(R.drawable.water_melon)
            images.add(R.drawable.banana)
            images.add(R.drawable.apple)
            images.add(R.drawable.grapes)

            val i = Intent(this@MarketActivity, FruitsActivity::class.java)
            i.putExtra("cat", "$category")
            startActivity(i)
        }else if(cat.equals("Vegetables")){
            images.clear()
            images.add(R.drawable.potato)
            images.add(R.drawable.tomato)
            images.add(R.drawable.carrot)
            images.add(R.drawable.corn)
            images.add(R.drawable.egg_plant)
            images.add(R.drawable.onion)

            val i = Intent(this@MarketActivity, VegActivity::class.java)
            i.putExtra("cat", "$category")
            startActivity(i)
        }
    }
}