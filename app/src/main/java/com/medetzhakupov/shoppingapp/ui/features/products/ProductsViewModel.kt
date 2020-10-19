package com.medetzhakupov.shoppingapp.ui.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.dispatchers.DispatcherProvider
import com.medetzhakupov.shoppingapp.repository.ProductsRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ProductsViewModel(
    val view: ProductsView,
    private val dispatcherProvider: DispatcherProvider,
    private val productsRepository: ProductsRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            withContext(dispatcherProvider.backgroundDispatcher) {
                productsRepository.loadProducts()
            }.also { view.updateState(ProductsViewState.Loaded(it)) }
        }
    }
}
