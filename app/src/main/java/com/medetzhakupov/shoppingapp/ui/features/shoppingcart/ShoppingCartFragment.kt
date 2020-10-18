package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medetzhakupov.shoppingapp.R

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ShoppingCartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_cart_fragment, container, false)
    }
}