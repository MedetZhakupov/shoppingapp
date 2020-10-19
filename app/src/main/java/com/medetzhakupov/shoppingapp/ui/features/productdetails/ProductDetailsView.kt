package com.medetzhakupov.shoppingapp.ui.features.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medetzhakupov.shoppingapp.Consumable
import com.medetzhakupov.shoppingapp.data.model.Product

//
// Created by Medet Zhakupov on 19/10/2020.
//
data class ProductDetailsViewState(val product: Product)

class ProductDetailsView(initialState: ProductDetailsViewState) {

    private val _addedToCartEvent = MutableLiveData<Consumable<Unit>>()

    val state: LiveData<ProductDetailsViewState> = MutableLiveData(initialState)
    val addedToCartEvent: LiveData<Consumable<Unit>> = _addedToCartEvent

    fun sendAddedToCartEvent() = _addedToCartEvent.postValue(Consumable(Unit))
}
