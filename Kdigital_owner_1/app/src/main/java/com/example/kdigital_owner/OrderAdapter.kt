package com.example.kdigital_owner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class OrderAdapter (private val context: Context): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var userList = mutableListOf<Order>()

    fun setListData(data:MutableList<Order>){
        userList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderAdapter.ViewHolder, position: Int) {
        val user : Order = userList[position]
        Glide.with(holder.itemView)
            .load(user.IMG)
            .into(holder.Img)
        holder.tableNuber.text= user.tablenumber.toString()
        holder.menu1.text= user.menu1
        holder.menu2.text= user.menu2
        holder.menu3.text= user.menu3
        holder.ea1.text= user.EA1.toString()
        holder.ea2.text= user.EA2.toString()
        holder.ea3.text= user.EA3.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }



    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val Img = itemView.findViewById<ImageView>(R.id.dogPhotoImg)
        val tableNuber = itemView.findViewById<TextView>(R.id.TableNumber)
        val menu1 = itemView.findViewById<TextView>(R.id.menu1Tv)
        val menu2 = itemView.findViewById<TextView>(R.id.menu2Tv)
        val menu3 = itemView.findViewById<TextView>(R.id.menu3Tv)
        val ea1 = itemView.findViewById<TextView>(R.id.menu1EA)
        val ea2 = itemView.findViewById<TextView>(R.id.menu2EA)
        val ea3 = itemView.findViewById<TextView>(R.id.menu3EA)

    }

}