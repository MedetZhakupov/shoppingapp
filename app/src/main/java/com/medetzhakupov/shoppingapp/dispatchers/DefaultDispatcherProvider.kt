package com.medetzhakupov.shoppingapp.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//
// Created by Medet Zhakupov on 18/10/2020.
//
object DefaultDispatcherProvider: DispatcherProvider {

    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    override val backgroundDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
}
