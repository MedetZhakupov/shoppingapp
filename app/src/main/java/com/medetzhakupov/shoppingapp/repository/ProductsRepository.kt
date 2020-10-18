package com.medetzhakupov.shoppingapp.repository

import com.medetzhakupov.shoppingapp.data.ProductsService
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.data.model.ProductType

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ProductsRepository(private val productsService: ProductsService) {

    fun loadProducts(): List<Product> = productsService.getProducts().filter(::isProductTypeKnown)

    private fun isProductTypeKnown(product: Product) = product.type != ProductType.UNKNOWN
}
