package com.medetzhakupov.shoppingapp.ui.features.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.dispatchers.DefaultDispatcherProvider
import com.medetzhakupov.shoppingapp.dispatchers.DispatcherProvider
import com.medetzhakupov.shoppingapp.extensions.observe
import com.medetzhakupov.shoppingapp.extensions.viewModel
import com.medetzhakupov.shoppingapp.repository.ProductsRepository
import kotlinx.android.synthetic.main.products_fragment.*

//
// Created by Medet Zhakupov on 17/10/2020.
//

class ProductsFragment(
    private val productsRepository: ProductsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider,
    onProductSelected: (Product) -> Unit
) : Fragment() {

    private val viewModel: ProductsViewModel by viewModel {
        ProductsViewModel(
            ProductsView(ProductsViewState.Loading),
            dispatcherProvider,
            productsRepository
        )
    }

    private val productsAdapter = ProductsAdapter(onProductSelected)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = productsAdapter
        }

        with(viewModel.view) {
            observe(state, ::render)
        }
    }

    private fun render(newState: ProductsViewState) {
        when (newState) {
            ProductsViewState.Loading -> {
            }
            is ProductsViewState.Loaded -> {
                productsAdapter.submitList(newState.products)
            }
        }
    }
}