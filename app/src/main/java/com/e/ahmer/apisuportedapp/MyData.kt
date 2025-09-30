package com.e.ahmer.apisuportedapp

data class MyData(
    val carts: List<Cart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)