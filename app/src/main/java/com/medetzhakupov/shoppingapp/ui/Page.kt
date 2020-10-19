package com.medetzhakupov.shoppingapp.ui

import android.os.Parcelable
import com.medetzhakupov.shoppingapp.data.model.Product
import kotlinx.android.parcel.Parcelize

//
// Created by Medet Zhakupov on 18/10/2020.
//
sealed class Page: Parcelable {
    @Parcelize object Products: Page()
    @Parcelize object ShoppingCart: Page()
    @Parcelize data class ProductDetails(val product: Product): Page()
}
