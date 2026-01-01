package com.example.demosample.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String?){
    msg?.let {
        Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
    }
}