package com.medetzhakupov.shoppingapp

import com.medetzhakupov.shoppingapp.data.model.Info
import com.medetzhakupov.shoppingapp.data.model.Price
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.data.model.ProductType

//
// Created by Medet Zhakupov on 18/10/2020.
//
val testProducts = listOf(
    Product(
        id = "1",
        name = "Henriksdal",
        price = Price(value = 499, currency = "kr"),
        info = Info(material = "wood with cover", color = "white"),
        type = ProductType.CHAIR,
        imageUrl = "https://shop.static.ingka.ikea.com/PIAimages/0462849_PE608354_S4.JPG"
    ),
    Product(
        id = "2",
        name = "Lidhult",
        price = Price(value = 1035, currency = "kr"),
        info = Info(numberOfSeats = 4, color = "beige"),
        type = ProductType.COUCH,
        imageUrl = "https://shop.static.ingka.ikea.com/PIAimages/0667779_PE714073_S4.JPG"
    )
)
