package com.yurdm.lab7

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentManager
import kotlin.random.Random

class StateReceiver(
    private val useNotification: Boolean = false,
    private val fragmentManager: FragmentManager
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = when (intent?.action.toString()) {
            Intent.ACTION_BATTERY_LOW -> context!!.getString(R.string.low_battery_title)
            Intent.ACTION_CAMERA_BUTTON -> context!!.getString(R.string.camera_button_title)
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> context!!.getString(R.string.airplane_title)
            else -> context!!.getString(R.string.unknown_title)
        }

        val message = when (intent?.action.toString()) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED ->
                if (intent?.getBooleanExtra("state", false) == true) {
                    context.getString(R.string.airplane_enabled_message)
                } else {
                    context.getString(R.string.airplane_disabled_message)
                }
            Intent.ACTION_BATTERY_LOW -> context.getString(R.string.low_battery_message)
            Intent.ACTION_CAMERA_BUTTON -> context.getString(R.string.camera_button_message)
            else -> ""
        }

        if (useNotification) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "IMPORTANT"
                val descText = "Important notifications"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val mChannel = NotificationChannel("1", name, importance)
                mChannel.description = descText

                val notificationManager =
                    context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(mChannel)
            }

            val builder = NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            NotificationManagerCompat.from(context).apply {
                this.notify(Random.nextInt(),builder.build())
            }
        } else {
            val dialog = StateDialog(title, message)
            dialog.show(fragmentManager, "dlg")
        }

        println(useNotification)
    }
}