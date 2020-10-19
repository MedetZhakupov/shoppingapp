package com.medetzhakupov.shoppingapp.ui.features.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.model.Product
import kotlinx.android.synthetic.main.product_item.view.*

//
// Created by Medet Zhakupov on 18/10/2020.
//
class ProductsAdapter(private val onProductSelected: (Product) -> Unit) :
    ListAdapter<Product, RecyclerView.ViewHolder>(ProductsDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) holder.bind(getItem(position)) { onProductSelected.invoke(it) }
    }

    class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        internal fun bind(product: Product, clickListener: (Product) -> Unit) {
            Glide.with(view).load(product.imageUrl).into(view.image)
            view.name_tv.text = product.name
            view.price_tv.text = "${product.price.value} ${product.price.currency.toUpperCase()}"
            view.setOnClickListener { clickListener.invoke(product) }
        }
    }
}

internal object ProductsDiff : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return false
    }
}
