package com.e.ahmer.apisuportedapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity() {

    //late defind variable for myrecycleview
    lateinit var myrecycleview : RecyclerView
    //late defind variable for myAdapter
    lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //myrecycleview is defind with id
        myrecycleview=findViewById(R.id.myrecycleview)

        //retrofit Builder code
        val retrofit= Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        //set  retrofitData with sep interface class
        val retrofitData=retrofit.getCartData()

        //set  two methods,if application success or failure(onResponse,onFailure)
        retrofitData.enqueue(object : Callback<My?> {

            override fun onResponse(call: Call<My?>, response: Response<My?>) {
                // if api call is a success, then use the data of API and show in your app

                //set response body for apni API wala products object
                val responseBody = response.body()
                val productlist = responseBody?.products!!

                //set adapter and layoutManager
                val myAdapter = MyAdapter(this@MainActivity, productlist)
                myrecycleview.adapter = myAdapter
                myrecycleview.layoutManager = LinearLayoutManager(this@MainActivity)

            }
            // if api call fails
            override fun onFailure(call: Call<My?>, t: Throwable) {
                Log.d("Main Activity ", "onFailure: " + t.message)
            }
        })

    }
}