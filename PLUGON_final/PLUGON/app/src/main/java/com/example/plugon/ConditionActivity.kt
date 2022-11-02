package com.example.plugon

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.plugon.databinding.ActivityConditionBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_condition.*
import kotlinx.android.synthetic.main.activity_condition.view.*

class ConditionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConditionBinding

    //파이어베이스 연동
    val database = Firebase.database
    val conRef = database.getReference("condition")
    val scheRef = database.getReference("schedule")
    //현재 온도, 습도
    var temperature: String = ""
    var humidity: String = ""
    //사용자가 설정한 온도, 습도
    var setTem: String = ""
    var setHum: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_condition)

        getIntent()

        //파이어베이스에서 현재 온도, 습도, 온오프 상태 받아오기
        conRef.child("temperature").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            binding.temperature.setText("${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        conRef.child("humidity").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            binding.humidity.setText("${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        scheRef.child("Relay").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            binding.onOff.setText("${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        //사용자 설정 온도, 습도 입력 버튼
        binding.setBtn.setOnClickListener {
            //현재 온도, 습도
            temperature = database.getReference("condition").child("temperature").toString()
            humidity = database.getReference("condition").child("humidity").toString()
            //사용자가 설정한 온도, 습도
            setTem = binding.setTemperature.text.toString()
            setHum = setHumidity.text.toString()
            conRef.child("user_tem").setValue(setTem)
            conRef.child("user_hum").setValue(setHum)
        }

        //자동 제어 버튼
        binding.switchDetect.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                conRef.child("state").setValue("ON")
                App.prefs.mySwitchState=true
                //체크 시 사용자 설정 온습도와 현재 온습도 비교하여 스위치 온오프
                if ((temperature.toFloat() >= setTem.toFloat()) || (humidity.toFloat() >= setHum.toFloat())){
                    scheRef.child("Relay").setValue("OFF")
                }
                else scheRef.child("Relay").setValue("ON")
            }
            else{
                conRef.child("state").setValue("OFF")
                App.prefs.mySwitchState=false
            }
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }
    }

}