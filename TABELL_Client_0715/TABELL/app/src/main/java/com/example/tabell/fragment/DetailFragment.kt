package com.example.tabell.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tabell.BagActivity
import com.example.tabell.R
import com.example.tabell.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*
class DetailFragment : DialogFragment() {

        private var _binding: FragmentDetailBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            _binding=FragmentDetailBinding.inflate(inflater, container, false)

            val name = binding.textViewName.text
            val view = binding.root
            val output = binding.textQuantity
            val upbtn = binding.btnUp
            val downbtn = binding.btnDown
            val bagbtn = binding.btnBag
            val closebtn = binding.btnClose
            var number = 1
            val priceNum = binding.textViewPrice.text.toString().toInt()
            var price = priceNum
            var totalPrice = priceNum
            val optionRGrp = binding.radioGroup
            var checkedBtn = optionRGrp.checkedRadioButtonId

            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            //옵션으로 가격 수정
            optionRGrp.setOnCheckedChangeListener { radioGroup, checkedBtn->
                when(checkedBtn){
                    R.id.option1 -> price= priceNum + 3000
                    R.id.option2 -> price= priceNum
                }
                totalPrice = price * number
                textView_price.text=totalPrice.toString()
            }

            //라디오버튼으로 수량 조정
            upbtn.setOnClickListener{
                number++
                output.setText(number.toString())
                totalPrice = price * number
                textView_price.text=totalPrice.toString()
            }

            downbtn.setOnClickListener{
                if (number>0){
                    number--
                    output.setText(number.toString())
                    totalPrice = price * number
                    textView_price.text=totalPrice.toString()
                }
            }

            //장바구니에 정보 전송(메뉴 프래그먼트>액티비티>장바구니 프래그먼트)

            bagbtn.setOnClickListener{
                val intent = Intent(context, BagActivity::class.java)

                //이름
                intent.putExtra("이름", name.toString())
            //수량
                intent.putExtra("수량", number.toString())
            //가격
                intent.putExtra("가격", totalPrice.toString())
            //옵션
                when(checkedBtn){
                    R.id.option1 -> intent.putExtra("옵션", "곱빼기")

                    R.id.option2 -> intent.putExtra("옵션", "보통")
                }
//                intent.putExtra("옵션", binding.radioGroup.checkedRadioButtonId.toString())

                //장바구니 액티비티에 전달 및 실행
                startActivity(intent)
            }

            //메인 액티비티로 복귀
            closebtn.setOnClickListener {
                dismiss()
            }

            //뷰 전달
            return view
        }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}