package com.medetzhakupov.shoppingapp

import androidx.lifecycle.Observer

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ConsumingObserver<T>(private val action: (T) -> Unit) : Observer<Consumable<T>> {

    override fun onChanged(consumable: Consumable<T>?) {
        consumable?.consume {
            action(it)
        }
    }
}

