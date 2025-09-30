package com.e.ahmer.apisuportedapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    fun getCartData() : Call<My>
}