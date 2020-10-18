package com.medetzhakupov.shoppingapp.ui.features

import androidx.fragment.app.*
import com.medetzhakupov.shoppingapp.R
import kotlinx.android.synthetic.main.activity_main.view.*

//
// Created by Medet Zhakupov on 18/10/2020.
//
abstract class MainFragmentFeature<T : Fragment>(private val activity: FragmentActivity) {

    private val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    private val tag get() = "main_fragment_${this::class.simpleName}"

    @Suppress("UNCHECKED_CAST")
    private val fragment: T?
        get() = fragmentManager.findFragmentByTag(tag) as? T

    fun show() {
        val newFragment = createFragment()

        fragmentManager.commit {
            replace(R.id.main_fragment_container, newFragment, tag)
        }
    }

    abstract fun createFragment(): T
}