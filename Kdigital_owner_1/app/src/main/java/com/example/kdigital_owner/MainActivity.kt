package com.example.kdigital_owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kdigital_owner.Fragment_menu
import com.example.kdigital_owner.Fragment_order
import com.example.kdigital_owner.Fragment_sales
import com.example.kdigital_owner.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bottomNavi = findViewById(R.id.bottomNavi) as BottomNavigationView

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        bottomNavi.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val FragmentMenu = Fragment_menu()
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, FragmentMenu).commit()
                }
                R.id.action_search -> {
                    val FragmentOrder = Fragment_order()
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, FragmentOrder).commit()
                }
                R.id.action_qrcode -> {
                    val FragmentSales = Fragment_sales()
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, FragmentSales).commit()
                }
            }
            true
        }
            selectedItemId = R.id.action_home
        }
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        var bottomNavi = findViewById(R.id.bottomNavi) as BottomNavigationView
//        setFrag(0)
//    }
//
//    private fun setFrag(fregNum : Int) {
//        val ft = supportFragmentManager.beginTransaction()
//        when(fregNum){
//            0 -> {ft.replace(R.id.main_frame, Fragment_menu()).commit()}
//            1 -> {ft.replace(R.id.main_frame, Fragment_order()).commit()}
//            2 -> {ft.replace(R.id.main_frame, Fragment_sales()).commit() }
//        }
//    }
}
