package com.example.finalproj.setting


import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.finalproj.R
import com.example.finalproj.firstActivity
import java.util.*



class AlarmReciver() : BroadcastReceiver(){
    companion object {
        val TAG = "AlarmReceiver"
        val NOTIFICATION_ID = 0
        val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    }
    lateinit var notificationManager: NotificationManager



    override fun onReceive(context: Context?, intent: Intent?) {

        notificationManager = context?.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel()
        deliverNotification(context)


    }
    fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, firstActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.coin)
                .setContentTitle("다 했나요?")
                .setContentText("오늘 할 일을 확인하세요")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setFullScreenIntent(contentPendingIntent, true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "alarm"
            notificationManager.createNotificationChannel(
                notificationChannel)
        }
    }

}
