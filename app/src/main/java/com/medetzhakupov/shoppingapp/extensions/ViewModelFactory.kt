package com.medetzhakupov.shoppingapp.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//
// Created by Medet Zhakupov on 19/10/2020.
//
internal inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    crossinline createViewModel: () -> T
) = lazy {
    ViewModelProvider(
        this,
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>) = createViewModel() as T
        }
    ).get(T::class.java)
}

internal inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline createViewModel: () -> T
) = lazy {
    ViewModelProvider(
        this,
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>) = createViewModel() as T
        }
    ).get(T::class.java)
}