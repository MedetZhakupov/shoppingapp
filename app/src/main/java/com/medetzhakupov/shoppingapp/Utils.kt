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

/**
 * Runs suspend function in background and returns results in main thread. If fails simply executes onFailure block
 */
@Suppress("detekt.TooGenericExceptionCaught", "detekt.RethrowCaughtException") // No error specific handling needed for this module
internal fun CoroutineScope.runInBackground(job: suspend () -> Unit, onFailure: (exception: Exception) -> Unit) =
    launch {
        try {
            job.invoke()
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            onFailure.invoke(exception)
        }
    }
