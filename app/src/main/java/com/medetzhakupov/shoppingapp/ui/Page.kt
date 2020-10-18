package com.medetzhakupov.shoppingapp.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//
// Created by Medet Zhakupov on 18/10/2020.
//
sealed class Page: Parcelable {
    @Parcelize object Products: Page()
    @Parcelize object ShoppingCart: Page()
}
