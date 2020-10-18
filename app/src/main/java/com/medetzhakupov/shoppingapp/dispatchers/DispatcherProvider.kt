package com.medetzhakupov.shoppingapp.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

//
// Created by Medet Zhakupov on 18/10/2020.
//

interface DispatcherProvider {

    /**
     * Main (UI) thread dispatcher.
     */
    val mainDispatcher: CoroutineDispatcher

    /**
     * Dispatcher for performing background tasks.
     */
    val backgroundDispatcher: CoroutineDispatcher
}