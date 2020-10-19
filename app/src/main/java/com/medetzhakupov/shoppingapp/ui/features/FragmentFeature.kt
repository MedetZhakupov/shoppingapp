package com.medetzhakupov.shoppingapp.ui.features

import androidx.fragment.app.*
import com.medetzhakupov.shoppingapp.R

//
// Created by Medet Zhakupov on 18/10/2020.
//
abstract class FragmentFeature<T : Fragment>(private val activity: FragmentActivity) {

    private val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    private val tag get() = "fragment_${this::class.simpleName}"

    @Suppress("UNCHECKED_CAST")
    protected val fragment: T?
        get() = fragmentManager.findFragmentByTag(tag) as? T

    fun show() {
        val newFragment = fragment ?: createFragment()

        fragmentManager.commit {
            replace(R.id.main_fragment_container, newFragment, tag)
            addToBackStack(null)
        }
    }

    abstract fun createFragment(): T
}