package com.medetzhakupov.shoppingapp.ui.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medetzhakupov.shoppingapp.data.model.Product

//
// Created by Medet Zhakupov on 18/10/2020.
//
sealed class ProductsViewState {
    object Loading : ProductsViewState()
    data class Loaded(val products: List<Product>) : ProductsViewState()
}

class ProductsView(initialState: ProductsViewState) {

    private val _state = MutableLiveData(initialState)

    val state: LiveData<ProductsViewState> = _state

    fun updateState(newState: ProductsViewState) {
        _state.postValue(newState)
    }
}
