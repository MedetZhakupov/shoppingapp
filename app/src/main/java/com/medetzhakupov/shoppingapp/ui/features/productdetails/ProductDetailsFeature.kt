package com.medetzhakupov.shoppingapp.ui.features.productdetails

import androidx.fragment.app.FragmentActivity
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import com.medetzhakupov.shoppingapp.ui.features.FragmentFeature

//
// Created by Medet Zhakupov on 19/10/2020.
//
class ProductDetailsFeature(
    private val product: Product,
    private val cartProductsRepository: CartProductsRepository,
    activity: FragmentActivity,
    private val onClose: () -> Unit
): FragmentFeature<ProductDetailsFragment>(activity) {

    override fun createFragment() = ProductDetailsFragment(cartProductsRepository, onClose).withArguments(product = product)
}