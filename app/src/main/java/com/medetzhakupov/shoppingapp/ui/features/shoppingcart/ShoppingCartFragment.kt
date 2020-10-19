package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.extensions.observe
import com.medetzhakupov.shoppingapp.extensions.viewModel
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import kotlinx.android.synthetic.main.products_fragment.recyclerview
import kotlinx.android.synthetic.main.shopping_cart_fragment.*

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ShoppingCartFragment(cartProductsRepository: CartProductsRepository) : Fragment() {

    private val viewModel: ShoppingCartViewModel by viewModel {
        ShoppingCartViewModel(
            ShoppingCartView(ShoppingCartViewState.Loading),
            cartProductsRepository
        )
    }

    private val shoppingCartAdapter = ShoppingCartAdapter(
        onAdd = { viewModel.add(it) },
        onRemove = { viewModel.remove(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = shoppingCartAdapter
        }

        with(viewModel.view) {
            observe(state, ::render)
        }
    }

    private fun render(newState: ShoppingCartViewState) {
        when (newState) {
            ShoppingCartViewState.Loading -> {
            }
            is ShoppingCartViewState.Loaded -> {
                total_amount_tv.text = getString(R.string.total_amount, newState.totalAmount)
                shoppingCartAdapter.submitList(newState.cartProducts)
            }
        }
    }
}