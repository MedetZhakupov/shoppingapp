package com.medetzhakupov.shoppingapp.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.medetzhakupov.shoppingapp.Consumable
import com.medetzhakupov.shoppingapp.ConsumingObserver
import java.util.concurrent.atomic.AtomicReference

//
// Created by Medet Zhakupov on 19/10/2020.
//
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

/**
 * Observes the given [observable] and invokes the given [observer] on every new emission
 */
inline fun <T> LifecycleOwner.observe(observable: LiveData<T>, crossinline observer: (T) -> Unit) {
    observable.observe(this, Observer { it?.run { observer(it) } })
}

/**
 * Observes the given consumable [observable] and invokes the given [observer] on every new emission, if it wasn't consumed yet
 */
inline fun <T> LifecycleOwner.observeConsumable(
    observable: LiveData<Consumable<T>>,
    crossinline observer: (T) -> Unit
) {
    observable.observe(this, ConsumingObserver { observer(it) })
}