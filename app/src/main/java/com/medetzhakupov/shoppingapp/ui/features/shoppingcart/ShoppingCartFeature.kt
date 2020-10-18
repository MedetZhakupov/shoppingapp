package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import androidx.fragment.app.FragmentActivity
import com.medetzhakupov.shoppingapp.ui.features.MainFragmentFeature

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ShoppingCartFeature(activity: FragmentActivity): MainFragmentFeature<ShoppingCartFragment>(activity) {

    override fun createFragment() = ShoppingCartFragment()
}
