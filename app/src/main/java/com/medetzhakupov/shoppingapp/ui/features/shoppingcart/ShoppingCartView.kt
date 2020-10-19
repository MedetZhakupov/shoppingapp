package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medetzhakupov.shoppingapp.data.cache.CartProduct
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.ui.features.products.ProductsViewState

//
// Created by Medet Zhakupov on 19/10/2020.
//
sealed class ShoppingCartViewState {
    object Loading : ShoppingCartViewState()
    data class Loaded(val totalAmount: Double, val cartProducts: List<CartProduct>) : ShoppingCartViewState()
}

class ShoppingCartView(initialState: ShoppingCartViewState) {

    private val _state = MutableLiveData(initialState)

    val state: LiveData<ShoppingCartViewState> = _state

    fun updateState(newState: ShoppingCartViewState) {
        _state.postValue(newState)
    }
}