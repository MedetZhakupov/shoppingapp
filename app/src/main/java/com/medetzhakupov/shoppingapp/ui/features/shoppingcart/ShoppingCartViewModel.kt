package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medetzhakupov.shoppingapp.data.cache.CartProduct
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

//
// Created by Medet Zhakupov on 19/10/2020.
//
@ExperimentalCoroutinesApi
class ShoppingCartViewModel(
    val view: ShoppingCartView,
    private val cartProductsRepository: CartProductsRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            cartProductsRepository.getAllCartProducts()
                .flowOn(Dispatchers.IO)
                .collect { cartProducts ->
                    view.updateState(
                        ShoppingCartViewState.Loaded(
                            cartProducts.sumByDouble { "%.2f".format(it.price * it.count).toDouble() },
                            cartProducts
                        )
                    )
                }
        }
    }

    fun remove(cartProduct: CartProduct) {
        viewModelScope.launch {
            if (cartProduct.count > 1) {
                cartProductsRepository.updateCartProduct(cartProduct.copy(count = cartProduct.count - 1))
            } else {
                cartProductsRepository.deleteCartProduct(cartProduct)
            }
        }
    }

    fun add(cartProduct: CartProduct) {
        viewModelScope.launch { cartProductsRepository.updateCartProduct(cartProduct.copy(count = cartProduct.count + 1)) }
    }


}