package com.example.klitaviy.retrypolicytest.core

/**
 * Created by klitaviy on 9/6/18-5:27 PM.
 */
object ExceptionProvider {
    fun getException(message: String = ""): Exception = Exception(message)
}