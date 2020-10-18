package com.medetzhakupov.shoppingapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Products(val products: List<Product>)
