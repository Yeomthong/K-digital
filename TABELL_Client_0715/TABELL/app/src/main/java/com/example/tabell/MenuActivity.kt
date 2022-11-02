package com.example.tabell

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.databinding.DataBindingUtil
import com.example.tabell.databinding.ActivityMenuBinding
import com.example.tabell.fragment.DetailFragment
import com.google.android.material.tabs.TabLayout
import kotlin.math.abs

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu)

        //스무스 스크롤 함수
        fun ScrollView.scrollToView(view: View) {
            val y = computeDistanceToView(view)
            this.smoothScrollTo(0, y)
        }

        //탭바 선택 시 해당하는 스크롤뷰로 이동
        binding.tabMenu.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        binding.scrollviewMenu.scrollToView(binding.bigmacLayout)
                    }

                    1 -> {
                        binding.scrollviewMenu.scrollToView(binding.frenchFriesLayout)
                    }

                    2 -> {
                        binding.scrollviewMenu.scrollToView(binding.cocacolaLayout)
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
//        binding.noodleBlackLayout.setOnClickListener(Layoutlistener())
//        binding.noodleRedLayout.setOnClickListener(Layoutlistener2())
//        binding.riceFriedLayout.setOnClickListener(Layoutlistener3())
//        binding.dishPigLayout.setOnClickListener(Layoutlistener4())

        //상품 옵션 프래그먼트 호출부
        binding.bigmacLayout.setOnClickListener{
            val dialog = DetailFragment()
            dialog.show(supportFragmentManager, "DetailFragment")
        }

        //장바구니 액티비티 전환
        binding.bagButton.setOnClickListener {
            val intent = Intent(this, BagActivity::class.java)
            startActivity(intent)
        }
    }

    //프래그먼트 생성
//    fun setFragment(fragment: Fragment, tag: String){
//        if (supportFragmentManager.findFragmentByTag("tag")==null) {
//            val transaction = supportFragmentManager.beginTransaction()
//                .add(R.id.noodle_black_layout, fragment, "tag")
//            transaction.commit()
//        }
//    }

    //프래그먼트에서 데이터 받아오기
//    fun setDataFromFragment(fragment: Fragment, tag: String, title: String){
//        val bundle=Bundle()
//        bundle.putString("title", title)
//
//        fragment.arguments=bundle
//        setFragment(fragment, tag)
//    }

    //스크롤 함수
    internal fun ScrollView.computeDistanceToView(view: View): Int {
        return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }
    //화면 좌표 계산 함수
    internal fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }

//    inner class Layoutlistener: View.OnClickListener{
//        override fun onClick(p0: View?) {
//            TODO("Not yet implemented")
//        }
//    }
//
//    inner class Layoutlistener2: View.OnClickListener{
//        override fun onClick(p0: View?) {
//            TODO("Not yet implemented")
//        }
//    }
//
//    inner class Layoutlistener3: View.OnClickListener{
//        override fun onClick(p0: View?) {
//            TODO("Not yet implemented")
//        }
//    }
//
//    inner class Layoutlistener4: View.OnClickListener{
//        override fun onClick(p0: View?) {
//            TODO("Not yet implemented")
//        }
//    }

}