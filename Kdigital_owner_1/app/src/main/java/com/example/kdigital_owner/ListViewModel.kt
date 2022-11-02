package com.example.kdigital_owner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel: ViewModel() {
    private val Fragment_order = com.example.kdigital_owner.Fragment_order.Repo()
    fun fetchData(): LiveData<MutableList<Order>> {
        val mutableData = MutableLiveData<MutableList<Order>>()
        Fragment_order.getData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}