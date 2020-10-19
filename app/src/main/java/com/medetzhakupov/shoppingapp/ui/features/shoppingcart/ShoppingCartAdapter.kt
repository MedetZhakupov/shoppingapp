package com.medetzhakupov.shoppingapp.ui.features.shoppingcart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.cache.CartProduct
import kotlinx.android.synthetic.main.product_item.view.image
import kotlinx.android.synthetic.main.product_item.view.name_tv
import kotlinx.android.synthetic.main.product_item.view.price_tv
import kotlinx.android.synthetic.main.shopping_cart_item.view.*

//
// Created by Medet Zhakupov on 19/10/2020.
//
class ShoppingCartAdapter(
    private val onAdd: (CartProduct) -> Unit,
    private val onRemove: (CartProduct) -> Unit
) : ListAdapter<CartProduct, RecyclerView.ViewHolder>(CartProductsDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_cart_item, parent, false)
        return CartProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartProductViewHolder) {
            holder.bind(getItem(position), { onAdd.invoke(it) }, { onRemove.invoke(it) })
        }
    }

    class CartProductViewHolder(
        private val view: View,
    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        internal fun bind(
            cartProduct: CartProduct,
            addClickListener: (CartProduct) -> Unit,
            removeClickListener: (CartProduct) -> Unit
        ) {
            Glide.with(view).load(cartProduct.imageUrl).into(view.image)
            view.name_tv.text = cartProduct.name
            view.price_tv.text = "${cartProduct.price} ${cartProduct.currency.toUpperCase()}"
            view.count_tv.text = "${cartProduct.count}"
            if (cartProduct.count > 1) {
                view.minus.setImageResource(R.drawable.ic_baseline_remove_24)
            } else {
                view.minus.setImageResource(R.drawable.ic_baseline_delete_24)
            }

            view.plus.setOnClickListener { addClickListener.invoke(cartProduct) }
            view.minus.setOnClickListener { removeClickListener.invoke(cartProduct) }
        }
    }
}

internal object CartProductsDiff : DiffUtil.ItemCallback<CartProduct>() {
    override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct) = oldItem.productId == newItem.productId

    override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return false
    }
}