package com.example.finalproj.setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.finalproj.R
import com.example.finalproj.firstActivity
import java.util.*

class BootAlarmReceiver(): BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {


        if("android.intent.action.BOOT_COMPLETED".equals(intent?.action)){
            val settings: SharedPreferences = context!!.getSharedPreferences(
                "ALARM",
                Context.MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = settings.edit()
            if(settings.getString("use_alarm","").equals("true")||settings.getString("use_alarm2","").equals("true")){
                val contentIntent = Intent(context, firstActivity::class.java)
                editor.putInt("alarm_hour", -1)
                editor.putInt("alarm_minute", -1)
                editor.putString("use_alarm","false")
                editor.putInt("alarm_hour2", -1)
                editor.putInt("alarm_minute2", -1)
                editor.putString("use_alarm2","false")
                editor.commit()
                val contentPendingIntent = PendingIntent.getActivity(
                    context,
                    AlarmReciver.NOTIFICATION_ID,
                    contentIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                 var notificationManager = context?.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

                val builder =
                    NotificationCompat.Builder(context, AlarmReciver.PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.coin)
                        .setContentTitle("다 했나요?")
                        .setContentText("알림 재설정이 필요합니다.")
                        .setContentIntent(contentPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setFullScreenIntent(contentPendingIntent, true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                notificationManager.notify(AlarmReciver.NOTIFICATION_ID, builder.build())
                fun createNotificationChannel() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val notificationChannel = NotificationChannel(
                            AlarmReciver.PRIMARY_CHANNEL_ID,
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

        }
    }

}
