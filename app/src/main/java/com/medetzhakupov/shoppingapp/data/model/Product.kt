package com.medetzhakupov.shoppingapp.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

//
// Created by Medet Zhakupov on 17/10/2020.
//
@JsonClass(generateAdapter = true)
@Parcelize
data class Product(
    @Json(name = "id") val id: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "info") val info: Info,
    @Json(name = "name") val name: String,
    @Json(name = "price") val price: Price,
    @Json(name = "type") val type: ProductType
) : Parcelable

enum class ProductType {
    @Json(name = "couch") COUCH,
    @Json(name = "chair") CHAIR,
    UNKNOWN
}
