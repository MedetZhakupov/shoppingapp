package com.medetzhakupov.shoppingapp.ui

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medetzhakupov.shoppingapp.extensions.AtomicMutableLiveData

//
// Created by Medet Zhakupov on 18/10/2020.
//
data class HomeViewState(val page: Page)

class HomeView(initialState: HomeViewState) {

    private val _state = AtomicMutableLiveData(initialValue = initialState)
    private val _shoppingCartEmpty = MutableLiveData(false)

    val state: LiveData<HomeViewState> = _state
    val shoppingCartEmptyEvent: LiveData<Boolean> = _shoppingCartEmpty

    fun updateState(update: (HomeViewState) -> HomeViewState) {
        _state.postUpdate(update)
    }

    fun sendShoppingCartEmptyEvent(isCartEmpty: Boolean) {
        _shoppingCartEmpty.postValue(isCartEmpty)
    }
}
