package com.medetzhakupov.shoppingapp.data

import android.content.res.Resources
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.data.model.ProductType
import com.medetzhakupov.shoppingapp.data.model.Products
import com.medetzhakupov.shoppingapp.readTextStream
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter

//
// Created by Medet Zhakupov on 17/10/2020.
//
class ProductsService(private val resources: Resources) {

    private val moshi = Moshi.Builder()
        .withUnknownFallbackForEnum(ProductType::class.java, ProductType.UNKNOWN)
        .build()
    private val adapter = moshi.adapter(Products::class.java)

    fun getProducts(): List<Product> {
        val `in` = resources.openRawResource(R.raw.products)
        return adapter.fromJson(readTextStream(`in`))?.products ?: emptyList()
    }

    private fun <T : Enum<T>> Moshi.Builder.withUnknownFallbackForEnum(
        enumClass: Class<T>,
        defaultForUnknown: T
    ) = apply {
        add(
            enumClass,
            EnumJsonAdapter.create(enumClass).withUnknownFallback(defaultForUnknown).nullSafe()
        )
    }
}
