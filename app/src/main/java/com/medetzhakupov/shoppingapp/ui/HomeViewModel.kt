package com.medetzhakupov.shoppingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

//
// Created by Medet Zhakupov on 18/10/2020.
//

@ExperimentalCoroutinesApi
class HomeViewModel(
    val view: HomeView,
    private val cartProductsRepository: CartProductsRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            cartProductsRepository.getFirstCartProduct()
                .collect { cartProducts ->
                    view.sendShoppingCartEmptyEvent(cartProducts.isEmpty())
                }
        }
    }

    fun onTapSelected(page: Page) {
        view.updateState { it.copy(page = page) }
    }

    fun onProductSelected(product: Product) {
        view.updateState {
            it.copy(page = Page.ProductDetails(product))
        }
    }

    fun onBackPressed() {
        view.updateState { it.copy(page = Page.Products) }
    }
}