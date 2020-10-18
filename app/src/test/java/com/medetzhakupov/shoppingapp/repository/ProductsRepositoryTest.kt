package com.medetzhakupov.shoppingapp.repository

import android.content.res.Resources
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.ProductsService
import com.medetzhakupov.shoppingapp.testProducts
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

//
// Created by  on 18/10/2020.
//

class ProductsRepositoryTest {

    private val resources: Resources = mock()
    private val productsService = ProductsService(resources)

    private val repository = ProductsRepository(productsService)

    @Test
    fun `should load list of products`() {
        whenever(resources.openRawResource(R.raw.products)).thenReturn(getResourceAsStream("responses/products.json"))

        val products = repository.loadProducts()

        assertThat(products).hasSameSizeAs(testProducts)
            .containsExactly(*testProducts.toTypedArray())
    }

    @Test
    fun `should filter out products with unknown type`() {
        whenever(resources.openRawResource(R.raw.products)).thenReturn(getResourceAsStream("responses/unknown_type.json"))

        val products = repository.loadProducts()
        val expectedProducts = listOf(testProducts.first())

        assertThat(products).hasSameSizeAs(expectedProducts)
            .containsExactly(*expectedProducts.toTypedArray())
    }

    private fun getResourceAsStream(path: String) =
        javaClass.classLoader?.getResourceAsStream(path)!!
}
