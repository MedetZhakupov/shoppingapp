package com.medetzhakupov.shoppingapp

import android.app.Application
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository

//
// Created by Medet Zhakupov on 19/10/2020.
//
class Injector(private val application: Application) {

    fun provideCartsProductsRepository() = CartProductsRepository.getInstance(application)
}