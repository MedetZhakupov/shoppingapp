package com.medetzhakupov.shoppingapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

//
// Created by Medet Zhakupov on 19/10/2020.
//
@Database(entities = [CartProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartProductDao(): CartProductDao
}