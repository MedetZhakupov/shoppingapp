package com.medetzhakupov.shoppingapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.MutableLiveData
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

class AtomicMutableLiveData<T>(initialValue: T) : MutableLiveData<T>() {
    private val state = AtomicReference(initialValue)

    init {
        super.postValue(initialValue)
    }

    override fun setValue(value: T) {
        state.set(value)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        state.set(value)
        super.postValue(value)
    }

    /**
     * Atomically updates the current value with the results of applying the given [update] function
     */
    fun postUpdate(update: (T) -> T) {
        val updatedValue = state.updateAndGetCompat(update)
        super.postValue(updatedValue)
    }

    /**
     * Atomically updates the current value with the results of applying the given [update] function
     * only if the given [condition] returns true for the current value
     */
    fun compareAndPostUpdate(condition: (T) -> Boolean, update: (T) -> T): Boolean {
        var wasConditionSatisfied = false

        val updatedValue = state.updateAndGetCompat { previousValue ->
            wasConditionSatisfied = condition(previousValue)

            if (wasConditionSatisfied) {
                update(previousValue)
            } else {
                previousValue
            }
        }

        if (wasConditionSatisfied) {
            super.postValue(updatedValue)
        }

        return wasConditionSatisfied
    }
}

/**
 * Atomically updates the current value with the results of
 * applying the given function, returning the updated value. The
 * function should be side-effect-free, since it may be re-applied
 * when attempted updates fail due to contention among threads.
 *
 * @param updateFunction a side-effect-free function
 * @return the updated value
 */
fun <V> AtomicReference<V>.updateAndGetCompat(updateFunction: (V) -> V): V {
    var prev: V
    var next: V
    do {
        prev = get()
        next = updateFunction.invoke(prev)
    } while (!compareAndSet(prev, next))
    return next
}
