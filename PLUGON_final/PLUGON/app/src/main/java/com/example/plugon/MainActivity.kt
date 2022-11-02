package com.example.plugon


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.widget.TimePicker
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.plugon.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener{

    lateinit var binding : ActivityMainBinding

    //파이어베이스 연동
    val database= Firebase.database
    val myRef=database.getReference("schedule").child("Relay")

    //파이어베이스 타이머 시간 저장 객체
    var t: String = ""

    val onButtonClick = { dialogInterface: DialogInterface, i: Int ->
        myRef.setValue("ON")
        toast("ON")
    }
    val offButtonClick = { dialogInterface: DialogInterface, i: Int ->
        myRef.setValue("OFF")
        toast("off")
    }

    //파이어베이스 스케쥴 클래스
    @IgnoreExtraProperties
    data class schedule(var LightPower: Int? = 0, var Relay: Int? = 0, var date: String? = ""){
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "LightPower" to LightPower,
                "Relay" to Relay,
                "date" to date
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyFirebaseMessagingService().getFirebaseToken()

        //플러그 작동 상태를 받아와서 스위치 온오프 표시
        val valueListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val getVal = dataSnapshot.value

                if (getVal == "ON"){
                    binding.imageView.backgroundTintList= ColorStateList.valueOf(Color.parseColor("#FFBDF67B"))
                    binding.buttonPower.text="ON"
                }
                else if (getVal == "OFF"){
                    binding.imageView.backgroundTintList= ColorStateList.valueOf(Color.parseColor("#FF4F738F"))
                    binding.buttonPower.text="OFF"
                }
            }

            override fun onCancelled(databaseError: DatabaseError){
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        myRef.addValueEventListener(valueListener)

        //수동 스위치 온오프
        binding.buttonPower.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("전등 제어")
                .setMessage("전등을 ON/OFF 하시겠습니까?")
                .setPositiveButton("ON",onButtonClick)
                .setNegativeButton("OFF", offButtonClick)
                .show()
        }

        //타이머
        binding.timebutton.setOnClickListener {
            var timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, "Time Picker")
        }

        //알람 취소
        binding.cancelbutton.setOnClickListener {
            cancelAlarm()
        }


        //CCTV
        binding.buttonCCTV.setOnClickListener{
            val intent = Intent(this, CCTVActivity::class.java)
            startActivity(intent)
        }

        //온습도 확인
        binding.buttonCondition.setOnClickListener {
            val intent = Intent(this, ConditionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cancelAlarm() {
        var alarmManager:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(this, AlertReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        alarmManager.cancel(pendingIntent)

        //알람 초기화 시 00:00:00으로 표시
        database.getReference("schedule").child("date").setValue("00:00:00")
    }

    override fun onTimeSet(timePicker: TimePicker?, hourOFDay: Int, minute: Int) {

        var c = Calendar.getInstance()

        c.set(Calendar.HOUR_OF_DAY, hourOFDay)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND,0)
        startAlarm(c)

        //t에 시:분:초 형식으로 데이터 저장
        t+=hourOFDay.toString()
        t+=":"
        t+=minute.toString()
        t+=":"
        t+="00"

        //schedule 클래스의 객체 date에 저장 후 t 초기화
        database.getReference("schedule").child("date").setValue(t)
        t = ""
    }

    private fun startAlarm(c: Calendar) {
        var alarmManager:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(this, AlertReceiver::class.java)
        var curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        var pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        intent.putExtra("time", curTime)
        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)

    }

    fun toast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

