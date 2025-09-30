package com.e.ahmer.apisuportedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MyAdapter(var activity: MainActivity, var productlist: List<ProductXX>) : RecyclerView.Adapter
    <MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(activity).inflate(R.layout.eachitemlist,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=productlist[position]
        holder.itemname.text=currentItem.title
        holder.retingText.text=currentItem.rating.toString()
        Picasso.get().load(currentItem.thumbnail).into(holder.image)
    }

    override fun getItemCount(): Int {
        return productlist.size
    }

    class MyViewHolder(viewitem : View) : RecyclerView.ViewHolder(viewitem) {

        var itemname : TextView
        var image : ShapeableImageView
        var retingText : TextView
        init {
            itemname=viewitem.findViewById(R.id.ItemName)
            image=viewitem.findViewById(R.id.ItemPic)
            retingText=viewitem.findViewById(R.id.ratintText)
        }

    }
}