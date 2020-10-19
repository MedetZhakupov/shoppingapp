package com.medetzhakupov.shoppingapp.ui.features.productdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.data.model.ProductType
import com.medetzhakupov.shoppingapp.extensions.observe
import com.medetzhakupov.shoppingapp.extensions.observeConsumable
import com.medetzhakupov.shoppingapp.extensions.viewModel
import com.medetzhakupov.shoppingapp.repository.CartProductsRepository
import kotlinx.android.synthetic.main.product_details_fragment.*

//
// Created by Medet Zhakupov on 19/10/2020.
//
private const val EXTRA_PRODUCT = "EXTRA_PRODUCT"

class ProductDetailsFragment(
    private val cartProductsRepository: CartProductsRepository,
    private val onClose: () -> Unit
) : Fragment() {

    fun withArguments(product: Product) = apply {
        arguments = bundleOf(EXTRA_PRODUCT to product)
    }

    private val product: Product
        get() = requireNotNull(arguments?.getParcelable(EXTRA_PRODUCT))

    private val viewModel: ProductDetailsViewModel by viewModel {
        ProductDetailsViewModel(
            ProductDetailsView(ProductDetailsViewState(product)),
            cartProductsRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel.view) {
            observe(state, ::render)
            observeConsumable(addedToCartEvent) {
                context?.also {
                    Toast.makeText(it, R.string.added_to_cart_message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        toolbar.setNavigationOnClickListener { onClose() }
        add_to_cart_button.setOnClickListener { viewModel.addToCart() }
    }

    @SuppressLint("SetTextI18n")
    private fun render(newState: ProductDetailsViewState) {
        newState.product.apply {
            Glide.with(requireView()).load(imageUrl).into(image_view)
            price_tv.text = "${price.value} ${price.currency.toUpperCase()}"
            name_tv.text = name
            when (type) {
                ProductType.CHAIR -> {
                    info_tv.text = getString(R.string.material, info.material)
                }
                ProductType.COUCH -> {
                    info_tv.text = getString(R.string.number_of_seats, info.numberOfSeats)
                }
                else -> info_tv.text = ""
            }

            color_tv.text = getString(R.string.color, info.color)
        }
    }
}