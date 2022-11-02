package com.example.tabell

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tabell.databinding.ActivityMainBinding
import com.example.tabell.fragment.*
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

private const val TAG_MAIN = "main_fragment"
private const val TAG_SEARCH = "search_fragment"
private const val TAG_QRCODE = "qrcode_fragment"
private const val TAG_FOODSTORE = "foodstore_fragment"
private const val TAG_SETTINGS = "settings_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_MAIN, MainFragment())

        binding.bottomNavi.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_home-> setFragment(TAG_MAIN, MainFragment())
                R.id.action_search -> setFragment(TAG_SEARCH, SearchFragment())
                R.id.action_qrcode-> setFragment(TAG_QRCODE, QrFragment())
                R.id.action_food -> setFragment(TAG_FOODSTORE, FoodstoreFragment())
                R.id.action_setting -> setFragment(TAG_SETTINGS, SettingsFragment())
            }
            true
        }

        AndPermission.with(this@MainActivity)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .permission(Permission.CAMERA)
            .onGranted { permissions ->
                Log.d("mmm", "허용된 권한 갯수 : ${permissions.size}")
            }
            .onDenied { permissions ->
                Log.d("mmm", "거부된 권한 갯수 : ${permissions.size}")
            }
            .start()
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.main_frame, fragment, tag)
        }

        val main = manager.findFragmentByTag(TAG_MAIN)
        val search = manager.findFragmentByTag(TAG_SEARCH)
        val qrcode = manager.findFragmentByTag(TAG_QRCODE)
        val foodstore = manager.findFragmentByTag(TAG_FOODSTORE)
        val settings = manager.findFragmentByTag(TAG_SETTINGS)

        if (main != null){
            fragTransaction.hide(main)
        }

        if (search != null){
            fragTransaction.hide(search)
        }

        if (qrcode != null) {
            fragTransaction.hide(qrcode)
        }

        if (foodstore != null) {
            fragTransaction.hide(foodstore)
        }

        if (settings != null) {
            fragTransaction.hide(settings)
        }

        if (tag == TAG_MAIN) {
            if (main!=null){
                fragTransaction.show(main)
            }
        }
        else if (tag == TAG_SEARCH) {
            if (search != null) {
                fragTransaction.show(search)
            }
        }

        else if (tag == TAG_QRCODE){
            if (qrcode != null){
                fragTransaction.show(qrcode)
            }
        }

        else if (tag == TAG_FOODSTORE){
            if (foodstore != null){
                fragTransaction.show(foodstore)
            }
        }

        else if (tag == TAG_SETTINGS){
            if (settings != null){
                fragTransaction.show(settings)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}
