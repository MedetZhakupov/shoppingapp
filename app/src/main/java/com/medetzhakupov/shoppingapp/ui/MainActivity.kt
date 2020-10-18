package com.medetzhakupov.shoppingapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.fragmentFactory
import com.medetzhakupov.shoppingapp.ui.features.products.ProductsFeature
import com.medetzhakupov.shoppingapp.ui.features.products.ProductsFragment
import com.medetzhakupov.shoppingapp.ui.features.shoppingcart.ShoppingCartFeature
import com.medetzhakupov.shoppingapp.ui.features.shoppingcart.ShoppingCartFragment

class MainActivity : AppCompatActivity() {

    private val productsFeature = ProductsFeature(this) {product ->

    }
    private val shoppingCartFeature = ShoppingCartFeature(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory(::createFragment)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun createFragment(fragmentClass: Class<out Fragment>): Fragment? = when(fragmentClass) {
        ProductsFragment::class.java -> {
            productsFeature.createFragment()
        }
        ShoppingCartFragment::class.java -> {
            shoppingCartFeature.createFragment()
        }
        else -> null
    }
}