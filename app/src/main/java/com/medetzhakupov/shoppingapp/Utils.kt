package com.medetzhakupov.shoppingapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.concurrent.atomic.AtomicReference

//
// Created by Medet Zhakupov on 17/10/2020.
//
@Throws(Exception::class)
fun readTextStream(inputStream: InputStream): String {
    val result = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var length: Int
    while ((inputStream.read(buffer).let { length = it; it != -1 })) {
        result.write(buffer, 0, length)
    }
    return result.toString("UTF-8")
}

inline fun fragmentFactory(crossinline makeFragment: (Class<out Fragment>) -> Fragment?) = object : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)

        return makeFragment(fragmentClass) ?: super.instantiate(classLoader, className)
    }
}
