package app.tukorea.client_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import app.tukorea.client_application.databinding.ActivityBagBinding

class BagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bag)

        val intent = getIntent()

        if (intent.hasExtra("이름")) {
            val name = intent.getStringExtra("이름")
            binding.textViewName.setText(name)
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
    }
}