package com.medetzhakupov.shoppingapp.ui.features.products

import androidx.fragment.app.FragmentActivity
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.ui.features.MainFragmentFeature

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ProductsFeature(
    activity: FragmentActivity,
    private val onProductSelected: (Product) -> Unit
): MainFragmentFeature<ProductsFragment>(activity) {

    override fun createFragment() = ProductsFragment(onProductSelected)
}
