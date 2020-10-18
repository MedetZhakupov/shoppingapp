package com.medetzhakupov.shoppingapp.ui.features.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.model.Product

//
// Created by Medet Zhakupov on 17/10/2020.
//

class ProductsFragment(onProductSelected: (Product) -> Unit) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }
}