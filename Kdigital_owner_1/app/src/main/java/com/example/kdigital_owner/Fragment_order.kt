package com.example.kdigital_owner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Fragment_order : Fragment() {
    private lateinit var adapter:OrderAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(ListViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        adapter = OrderAdapter(requireContext())

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        Repo()
        observerData()

        return view

    }

    class Repo {
        fun getData(): LiveData<MutableList<Order>> {
            val mutableData = MutableLiveData<MutableList<Order>>()
            val database = Firebase.database
            val myRef = database.getReference("Order")
            myRef.addValueEventListener(object : ValueEventListener {
                val listData: MutableList<Order> = mutableListOf<Order>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val getData = userSnapshot.getValue(Order::class.java)
                            listData.add(getData!!)
                            mutableData.value = listData
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
            return mutableData
        }
    }

    fun observerData(){
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })
    }
}

