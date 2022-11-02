package com.example.kdigital_owner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kdigital_owner.ListLayout
import com.example.kdigital_owner.R

class ListAdapter(val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
//        val binding = ListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecyclerView.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.price.text = itemList[position].price
        holder.classification.text = itemList[position].classification
        holder.oction1.text = itemList[position].oction1
        holder.oction1price.text = itemList[position].oction1price
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.list_tv_name)
        val price: TextView = itemView.findViewById(R.id.list_tv_price)
        val classification: TextView = itemView.findViewById(R.id.list_tv_classification)
        val oction1: TextView = itemView.findViewById(R.id.list_tv_Octin1)
        val oction1price: TextView = itemView.findViewById(R.id.list_tv_Oction1_price)
    }
}