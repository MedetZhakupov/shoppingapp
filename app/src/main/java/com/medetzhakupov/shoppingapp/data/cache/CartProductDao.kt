package com.medetzhakupov.shoppingapp.data.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//
// Created by Medet Zhakupov on 19/10/2020.
//

@Dao
interface CartProductDao {
    @Update
    suspend fun updateCartProducts(vararg cartProducts: CartProduct)

    @Query("SELECT * FROM cartproduct")
    fun getAll(): Flow<List<CartProduct>>

    @Query("SELECT * FROM cartproduct ORDER BY uid LIMIT 1")
    fun getOne(): Flow<List<CartProduct>>

    @Query("SELECT * FROM cartproduct WHERE productId=:productId")
    suspend fun getCartProduct(productId: String): CartProduct?

    @Insert
    suspend fun insertAll(vararg cartProduct: CartProduct)

    @Delete
    suspend fun delete(cartProduct: CartProduct)
}