package com.e.ahmer.apisuportedapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("CAST_NEVER_SUCCEEDS", "LocalVariableName")
@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity() {

    val channelId="channel id"
    val channelName="channel name"
    val notificationId=0
    @SuppressLint("MissingPermission")

    //late defind variable for myrecycleview
    lateinit var myrecycleview : RecyclerView
    //late defind variable for myAdapter
    lateinit var myAdapter: MyAdapter



    @SuppressLint("MissingPermission")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //defind the MyNotificationChannel func.
        MyNotificationChannel()

        //pending intent code
        val intent= Intent(this, MainActivity::class.java)
        val pendingIntent= PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_MUTABLE)


        //set the notification display
        val Notification= NotificationCompat.Builder(this,channelId)
            .setContentTitle("My Notification")
            .setContentText("I check the andiod notification Systems")
            .setSmallIcon(R.drawable.baseline_message_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        //manage the create notification
        val notificationManager= NotificationManagerCompat.from(this)

        //notify the my own notification
        val notificationBtn=findViewById<ImageButton>(R.id.imageButton)

        notificationBtn.setOnClickListener {
            notificationManager.notify(notificationId,Notification)
        }


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

    // MyNotificationChannel func. for oreo version android
    @Suppress("FunctionName")
    @SuppressLint("ObsoleteSdkInt")
    private fun MyNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channels= NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description="This is my Notification"
                lightColor= Color.GREEN
                enableLights(true)
            }
            val manager= getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channels)

        }
    }
}