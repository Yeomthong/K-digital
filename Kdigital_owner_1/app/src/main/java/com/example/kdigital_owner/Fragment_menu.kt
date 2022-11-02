package com.example.kdigital_owner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kdigital_owner.databinding.ActivityMainBinding
import android.widget.TextView
import android.widget.Button
import com.example.kdigital_owner.databinding.FragmentMenuBinding


class Fragment_menu : Fragment()  {

    private var mBinding: FragmentMenuBinding? = null   // 뷰 바인딩
    private val binding get() = mBinding!!
    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
    //private val itemList: ArrayList<ListLayout<String>> = arrayListOf()
    val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //val view = binding.root
        //setContentView(view)

        //val view = inflater.inflate(R.layout.fragment_menu, container, false)
        mBinding = FragmentMenuBinding.inflate(inflater, container, false)

//        var btnWrite: Button = findViewById(R.id.btn_write)
//
//        btnWrite.setOnClickListener {
        binding.btnWrite.setOnClickListener{
            // 대화상자 생성
            val builder = AlertDialog.Builder(requireContext())

            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tvName = TextView(requireContext())
            tvName.text = "Name"
            val etName = EditText(requireContext())
            etName.isSingleLine = true

            val tvPrice = TextView(requireContext())
            tvPrice.text = "Price"
            val etPrice = EditText(requireContext())
            etPrice.isSingleLine = true

            val tvClassification = TextView(requireContext())
            tvClassification.text = "Classification"
            val etclassification= EditText(requireContext())
            etclassification.isSingleLine = true

            val tvOction1 = TextView(requireContext())
            tvOction1.text = "Oction1"
            val etOction1 = EditText(requireContext())
            etOction1.isSingleLine = true

            val tvOction1Price = TextView(requireContext())
            tvOction1Price.text = "Oction1Price"
            val etOction1Price= EditText(requireContext())
            etOction1Price.isSingleLine = true

            val mLayout = LinearLayout(requireContext())

            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)

            mLayout.addView(tvName)
            mLayout.addView(etName)
            mLayout.addView(tvPrice)
            mLayout.addView(etPrice)
            mLayout.addView(tvClassification)
            mLayout.addView(etclassification)

            mLayout.addView(tvOction1)
            mLayout.addView(etOction1)
            mLayout.addView(tvOction1Price)
            mLayout.addView(etOction1Price)


            builder.setView(mLayout)
            builder.setTitle("데이터 추가")
            builder.setPositiveButton("추가") { dialog, which ->
                // EditText에서 문자열을 가져와 hashMap으로 만듦
                val data = hashMapOf(
                    "name" to etName.text.toString(),
                    "price" to etPrice.text.toString(),
                    "classification" to etclassification.text.toString(),
                    "oction1" to etOction1.text.toString(),
                    "oction1price" to etOction1Price.text.toString(),
                )

                // Contacts 컬렉션에 data를 자동 이름으로 저장
                db.collection("Contacts")
                    .add(data)
                    .addOnSuccessListener {
                        // 성공할 경우
                        Toast.makeText(requireContext(), "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우
                        Log.w("MainActivity", "Error getting documents: $exception")
                    }
            }
            builder.setNegativeButton("취소") { dialog, which ->

            }
            builder.show()
        }

        binding.rvList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter

        binding.btnRead.setOnClickListener {
            db.collection("Contacts")   // 작업할 컬렉션
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item = ListLayout(document["name"] as String, document["price"] as String,  document["classification"] as String,
                            document["oction1"] as String,  document["oction1price"] as String)
                        itemList.add(item)
                    }
                    adapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }
        return binding.root
    }
    // 프래그먼트가 destroy (파괴) 될때.
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }

}