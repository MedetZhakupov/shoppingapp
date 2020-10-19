package com.medetzhakupov.shoppingapp

import java.util.concurrent.atomic.AtomicBoolean

//
// Created by Medet Zhakupov on 18/10/2020.
//
class Consumable<out T>(
    val value: T
) {
    private val isConsumed = AtomicBoolean(false)

    fun consume(action: (T) -> Unit) {
        if (isConsumed.compareAndSet(false, true)) {
            action(value)
        }
    }
}
