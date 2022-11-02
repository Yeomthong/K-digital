package com.example.plugon

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val prefsFilename="prefs"
    private val prefsSwitch= "mySwitchState"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var mySwitchState: Boolean
        get()=prefs.getBoolean(prefsSwitch, false)
        set(value)=prefs.edit().putBoolean(prefsSwitch, value).apply()
}