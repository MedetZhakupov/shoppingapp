package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import androidx.fragment.app.FragmentActivity
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import com.medetzhakupov.shoppingapp.ui.features.FragmentFeature

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ShoppingCartFeature(
    private val cartProductsRepository: CartProductsRepository,
    activity: FragmentActivity
) : FragmentFeature<ShoppingCartFragment>(activity) {

    override fun createFragment() = ShoppingCartFragment(cartProductsRepository)
}
