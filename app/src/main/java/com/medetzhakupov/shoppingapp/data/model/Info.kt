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
data class Info(
    @Json(name = "color") val color: String,
    @Json(name = "material") val material: String? = null,
    @Json(name = "numberOfSeats") val numberOfSeats: Int = 0
) : Parcelable