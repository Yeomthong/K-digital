package com.example.plugon

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.plugon.databinding.ActivityCctvBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CCTVActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCctvBinding

    //파이어베이스 연동
    val database = Firebase.database
    val camRef = database.getReference("moter")
    val detectRef = database.getReference("DetectHuman")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCctvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //cctv 상하좌우 이동 버튼
        val btnUP = binding.buttonUP
        val btnDown = binding.buttonDOWN
        val btnLeft = binding.buttonLEFT
        val btnRight = binding.buttonRIGHT
        //cctv 화면
        val webView = binding.cctvWebView

        //각도 객체
        lateinit var curValue : AngleValue
        var curHeight: Int
        var curWidth: Int

        binding.switchDetect.setChecked(App.prefs.mySwitchState)

        //현재 각도 읽어오기
        val valueListener = object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val getVal = dataSnapshot.getValue<AngleValue>()
                curValue = getVal as AngleValue
            }

            override fun onCancelled(databaseError: DatabaseError){
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        camRef.addValueEventListener(valueListener)
        //각도 비교, 0~180도
        fun compareAngle(curAngle:Int, path:String){
            if (curAngle in 0 .. 180)
                camRef.child(path).setValue(curAngle)
            else
                Toast.makeText(this, "회전 범위를 넘어섰습니다.", Toast.LENGTH_SHORT).show()
        }

        //각 버튼 클릭마다 10도씩 증감
        btnUP.setOnClickListener {
            curHeight = curValue.height
            curHeight = curHeight - 10
            compareAngle(curHeight, "height")

        }

        btnDown.setOnClickListener {
            curHeight = curValue.height
            curHeight = curHeight + 10
            compareAngle(curHeight, "height")
        }

        btnLeft.setOnClickListener{
            curWidth = curValue.width
            curWidth = curWidth - 10
            compareAngle(curWidth, "width")

        }

        btnRight.setOnClickListener{
            curWidth = curValue.width
            curWidth = curWidth+10
            compareAngle(curWidth, "width")

        }

        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled=true
        }

        //웹뷰에 표시할 주소
        webView.loadUrl("http://192.168.176.61:8080/stream_simple.html")

        //침입자 알림 켜기
        binding.switchDetect.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                detectRef.child("state").setValue("ON")
                App.prefs.mySwitchState = true
            }
            else{
                detectRef.child("state").setValue("OFF")
                App.prefs.mySwitchState = false
            }
        }

        binding.buttonBacktoMain.setOnClickListener {
            finish()
        }
    }
}

@IgnoreExtraProperties
data class AngleValue(val height: Int = 0, val width: Int = 0) { //초기값 0으로 설정
}

