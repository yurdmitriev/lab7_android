package com.yurdm.lab7

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {
    private lateinit var receiver: StateReceiver
    private lateinit var filter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_CAMERA_BUTTON)
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        receiver = setReceiver(radioGroup.checkedRadioButtonId == R.id.radioNotification)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            unregisterReceiver(receiver)
            receiver = setReceiver(checkedId == R.id.radioNotification)
            registerReceiver(receiver, filter)
        }

        registerReceiver(receiver, filter)
    }

    private fun setReceiver(isNotification: Boolean): StateReceiver {
        return StateReceiver(isNotification, supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}