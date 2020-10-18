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
data class Price(
    @Json(name = "currency") val currency: String,
    @Json(name = "value") val value: Int
) : Parcelable