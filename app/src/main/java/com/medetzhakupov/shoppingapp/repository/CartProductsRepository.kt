package com.medetzhakupov.shoppingapp.repository

import android.app.Application
import androidx.room.Room
import com.medetzhakupov.shoppingapp.SingletonHolder
import com.medetzhakupov.shoppingapp.data.cache.AppDatabase
import com.medetzhakupov.shoppingapp.data.cache.CartProduct
import com.medetzhakupov.shoppingapp.data.cache.Type
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.data.model.ProductType
import com.medetzhakupov.shoppingapp.dispatchers.DefaultDispatcherProvider
import com.medetzhakupov.shoppingapp.dispatchers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn

//
// Created by Medet Zhakupov on 19/10/2020.
//
class CartProductsRepository(
    app: Application,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) {

    private val db = Room.databaseBuilder(
        app,
        AppDatabase::class.java, "shopping-cart"
    ).build()

    @ExperimentalCoroutinesApi
    fun getAllCartProducts() =
        db.cartProductDao().getAll().flowOn(dispatcherProvider.backgroundDispatcher)

    @ExperimentalCoroutinesApi
    fun getFirstCartProduct() =
        db.cartProductDao().getOne().flowOn(dispatcherProvider.backgroundDispatcher)

    suspend fun getCartProduct(productId: String) = db.cartProductDao().getCartProduct(productId)

    suspend fun updateCartProduct(cartProduct: CartProduct) =
        db.cartProductDao().updateCartProducts(cartProduct)

    suspend fun addProductToCart(product: Product) {
        db.cartProductDao().insertAll(
            CartProduct(
                productId = product.id,
                name = product.name,
                imageUrl = product.imageUrl,
                color = product.info.color,
                material = product.info.material,
                numberOfSeats = product.info.numberOfSeats,
                price = product.price.value,
                currency = product.price.currency,
                type = when (product.type) {
                    ProductType.CHAIR -> Type.CHAIR
                    ProductType.COUCH -> Type.COUCH
                    ProductType.UNKNOWN -> {
                        throw IllegalArgumentException("UNKNOWN product type")
                    }
                },
                count = 1
            )
        )
    }

    suspend fun deleteCartProduct(cartProduct: CartProduct) =
        db.cartProductDao().delete(cartProduct)

    companion object :
        SingletonHolder<CartProductsRepository, Application>(::CartProductsRepository)
}
