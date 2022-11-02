package com.example.tabell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tabell.databinding.ActivityBagBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBagBinding
    var orderList= arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bag)

        val intent = getIntent()

        if (intent.hasExtra("이름")) {
            val name = intent.getStringExtra("이름")
            binding.textViewName.setText(name)
            orderList.add(name.toString())
        }

        if (intent.hasExtra("수량")) {
            val quantity = intent.getStringExtra("수량")
            binding.textViewQuantity.setText(quantity)
        }

        if (intent.hasExtra("가격")) {
            val price = intent.getStringExtra("가격")
            binding.textViewPrice.setText(price)
        }

        if (intent.hasExtra("옵션")) {
            val option = intent.getStringExtra("옵션")
            binding.textViewOption.setText(option)
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }

        //전송 버튼 클릭 시 데이터베이스에 주문 전달(결제 버튼 대체)
        binding.sendbutton.setOnClickListener{
            var sendName=binding.textViewName.text.toString()
            var sendQuantity=binding.textViewQuantity.text.toString().toInt()
            var orderNum=orderList.size
            var a: Int = 0

            //while (a<=orderNum){
                val keyname="menu"+(a+1).toString()
                val keyQuantity="EA"+(a+1).toString()
                sendOrder(keyname,sendName)
                sendOrder(keyQuantity, sendQuantity)
                //a++
            //}
        }
    }

}

//DB로 데이터 전송: 테이블 번호, 메뉴 이름, 수량 전송, 이미지는 미구현
private fun sendOrder(key:String, value: Any){


    var tableNum=5 //테이블 번호는 임의로 지정함.

    val database=Firebase.database
    val myRef=database.getReference("Order").child(tableNum.toString())

    myRef.child(key).setValue(value)
    myRef.child("tablenumber").setValue(tableNum)

}