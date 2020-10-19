package com.medetzhakupov.shoppingapp.ui.features.products

import androidx.fragment.app.FragmentActivity
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.dispatchers.DispatcherProvider
import com.medetzhakupov.shoppingapp.repository.ProductsRepository
import com.medetzhakupov.shoppingapp.ui.features.FragmentFeature

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ProductsFeature(
    activity: FragmentActivity,
    private val productsRepository: ProductsRepository,
    private val onProductSelected: (Product) -> Unit
) : FragmentFeature<ProductsFragment>(activity) {

    override fun createFragment() =
        ProductsFragment(
            productsRepository = productsRepository,
            onProductSelected = onProductSelected
        )
}
