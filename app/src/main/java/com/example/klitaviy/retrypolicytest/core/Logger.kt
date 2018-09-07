package com.example.klitaviy.retrypolicytest.core

import android.util.Log

/**
 * Created by klitaviy on 9/6/18-5:24 PM.
 */
object Logger {
    private val tag = "my_awesome_tag"

    fun log(message: String) {
        Log.d(tag, message)
    }
}