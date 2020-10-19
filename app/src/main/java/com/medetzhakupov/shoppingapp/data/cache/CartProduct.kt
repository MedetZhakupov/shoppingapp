package com.medetzhakupov.shoppingapp.data.cache

import androidx.room.*

//
// Created by Medet Zhakupov on 19/10/2020.
//

@TypeConverters(ProductTypeConverter::class)
@Entity
data class CartProduct(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "productId") val productId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "material") val material: String?,
    @ColumnInfo(name = "numberOfSeats") val numberOfSeats: Int?,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "type") val type: Type,
    @ColumnInfo(name = "count") val count: Int
)

enum class Type {
    CHAIR,
    COUCH
}

class ProductTypeConverter {

    @TypeConverter
    fun fromString(type: String) = when (type) {
        "CHAIR" -> Type.CHAIR
        "COUCH" -> Type.COUCH
        else -> throw IllegalArgumentException("Unsupported product type")
    }

    @TypeConverter
    fun toEnum(type: Type) = when (type) {
        Type.CHAIR -> "CHAIR"
        Type.COUCH -> "COUCH"
    }

}