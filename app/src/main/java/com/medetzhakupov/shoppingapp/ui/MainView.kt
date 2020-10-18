package com.medetzhakupov.shoppingapp.ui

import androidx.lifecycle.LiveData
import com.medetzhakupov.shoppingapp.AtomicMutableLiveData

//
// Created by Medet Zhakupov on 18/10/2020.
//
data class MainViewState(val page: Page)

class MainView(initialState: MainViewState) {

    private val _state = AtomicMutableLiveData(initialValue = initialState)

    val state: LiveData<MainViewState> = _state

}