package com.greencrop.singleton

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.toast( msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Context.intent(className: Class<*>) {
    var i=Intent(this,className)
    startActivity(i)
}