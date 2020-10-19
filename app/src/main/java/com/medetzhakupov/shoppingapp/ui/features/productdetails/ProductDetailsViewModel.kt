package com.medetzhakupov.shoppingapp.ui.features.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

//
// Created by Medet Zhakupov on 19/10/2020.
//
class ProductDetailsViewModel(
    val view: ProductDetailsView,
    private val cartProductsRepository: CartProductsRepository
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun addToCart() {
        viewModelScope.launch {
            view.state.value?.let { viewState ->
                val cartProduct = cartProductsRepository.getCartProduct(viewState.product.id)
                if (cartProduct == null) {
                    cartProductsRepository.addProductToCart(viewState.product)
                } else {
                    cartProductsRepository.updateCartProduct(cartProduct.copy(count = cartProduct.count + 1))
                }

                view.sendAddedToCartEvent()
            }
        }
    }
}
