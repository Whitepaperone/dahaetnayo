package com.example.finalproj.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproj.R
import com.example.finalproj.store.Data_Theme
import kotlinx.android.synthetic.main.setting_alarm.*
import java.util.*

class use_alarm_Activity: AppCompatActivity() {
    fun changeTheme(){
        val themepref=this.getSharedPreferences("STORE_PREF",0)
        if(themepref.getInt(Data_Theme.Theme1.itemName, 0).equals(2)) {
            setTheme(R.style.Basic)
        }
        if(themepref.getInt(Data_Theme.Theme2.itemName, 0).equals(2)) {
            setTheme(R.style.Icecream)
        }
        if(themepref.getInt(Data_Theme.Theme3.itemName, 0).equals(2)) {
            setTheme(R.style.Red)
        }
        if(themepref.getInt(Data_Theme.Theme4.itemName, 0).equals(2)) {
            setTheme(R.style.Pink)
        }
        if(themepref.getInt(Data_Theme.Theme5.itemName, 0).equals(2)) {
            setTheme(R.style.Yellow)
        }
        if(themepref.getInt(Data_Theme.Theme6.itemName, 0).equals(2)) {
            setTheme(R.style.Green)
        }
        if(themepref.getInt(Data_Theme.Theme7.itemName, 0).equals(2)) {
            setTheme(R.style.Sea)
        }
        if(themepref.getInt(Data_Theme.Theme8.itemName, 0).equals(2)) {
            setTheme(R.style.Blue)
        }
        if(themepref.getInt(Data_Theme.Theme9.itemName, 0).equals(2)) {
            setTheme(R.style.Purple)
        }
        if(themepref.getInt(Data_Theme.Theme10.itemName, 0).equals(2)) {
            setTheme(R.style.Violet)
        }
        if(themepref.getInt(Data_Theme.Theme11.itemName, 0).equals(2)) {
            setTheme(R.style.Coffee)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_alarm)
        val settings: SharedPreferences = getSharedPreferences("ALARM", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()
        var state = 0
        var count = 0
        var state2 = 0
        var count2 = 0
        var real_hour:Int = -1

        if(settings.getString("use_alarm","").equals("true")){
            add_alarm.isChecked = true
            alarm_change.visibility = View.VISIBLE
            state = 1

        }
        else if(settings.getString("use_alarm","").equals("false")){
            add_alarm.isChecked = false
            alarm_change.visibility = View.GONE
            state = 0
        }
        else{
            editor.putString("use_alarm", "false")
        }


        //두 번째
        if(settings.getString("use_alarm2","").equals("true")){
            add_alarm2.isChecked = true
            alarm_change2.visibility = View.VISIBLE

            state2 = 1

        }
        else if(settings.getString("use_alarm2","").equals("false")){
            add_alarm2.isChecked = false
            alarm_change2.visibility = View.GONE
            state2 = 0
        }
        else{
            editor.putString("use_alarm2", "false")
        }


        var cal = Calendar.getInstance()
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReciver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmReciver.NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val repeatInterval: Long = AlarmManager.INTERVAL_DAY


        add_alarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                var timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    if (state == 0) {
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)


                        if (cal.get(Calendar.AM_PM) == Calendar.AM) {
                            editor.putString("am_pm", "am")
                            editor.commit()
                            real_hour = hour
                        } else {
                            editor.putString("am_pm", "pm")
                            editor.commit()
                            real_hour = hour + 12

                        }
                        editor.putInt("alarm_hour", hour)
                        editor.putInt("alarm_minute", minute)
                        editor.putString("use_alarm", "true")
                        editor.commit()


                        alarmManager.cancel(pendingIntent)

                        cal.apply {
                            timeInMillis = System.currentTimeMillis()
                            set(Calendar.HOUR_OF_DAY, hour)
                            set(Calendar.MINUTE, minute)
                            set(Calendar.SECOND, 0)
                        }
                        alarmManager.setInexactRepeating(
                            AlarmManager.RTC_WAKEUP,
                            cal.timeInMillis,
                            repeatInterval,
                            pendingIntent
                        )
                        Toast.makeText(applicationContext, "알림 설정 완료", Toast.LENGTH_SHORT).show()
                        alarm_change.visibility = View.VISIBLE
                        state = 1
                    }
                    if (state == 1) {
                        add_alarm.isChecked = true
                    }
                }
                if(state!=1){
                    TimePickerDialog(this,  timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
                    add_alarm.isChecked = false
                }

            }
            else{
                editor.putInt("alarm_hour", -1)
                editor.putInt("alarm_minute", -1)
                editor.putString("use_alarm","false")
                editor.commit()
                alarm_change.visibility = View.GONE
                add_alarm.isChecked = false
                state = 0; real_hour = -1
                alarmManager.cancel(pendingIntent)
            }
        }
        alarm_change.setOnClickListener{
            var timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if(cal.get(Calendar.AM_PM)==Calendar.AM&&!settings.getString("am_pm","").equals("am")) {
                    editor.putString("am_pm", "am")
                    editor.commit()
                    count = 1
                    real_hour = hour
                }
                else if(cal.get(Calendar.AM_PM)==Calendar.PM&&!settings.getString("am_pm","").equals("pm")){
                    editor.putString("am_pm", "pm")
                    editor.commit()
                    count = 1
                    real_hour = hour + 12
                }


                if(settings.getInt("alarm_hour",-1) != hour){
                    editor.putInt("alarm_hour", hour)
                    editor.commit()
                    count = 1
                }
                else if(settings.getInt("alarm_minute",-1)!=minute){
                    editor.putInt("alarm_minute", minute)
                    editor.commit()
                    count = 1
                }
                if(count == 1){
                    alarmManager.cancel(pendingIntent) //기존 거 취소

                    cal.apply {
                        timeInMillis = System.currentTimeMillis()
                        set(Calendar.HOUR_OF_DAY, real_hour)
                        set(Calendar.MINUTE, minute)
                        set(Calendar.SECOND, 0)
                    }
                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        cal.timeInMillis,
                        repeatInterval,
                        pendingIntent
                    )
                    Toast.makeText(applicationContext, "알림 수정 완료", Toast.LENGTH_SHORT).show()
                    count = 0
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

        }


        //두 번째
        var real_hour2:Int = -1
        val alarmManager2 = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent2 = Intent(this, AlarmReceiver2::class.java)
        val pendingIntent2 = PendingIntent.getBroadcast(
            this, AlarmReciver.NOTIFICATION_ID, intent2,
            PendingIntent.FLAG_UPDATE_CURRENT)
        add_alarm2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                var timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                    if(state2 == 0) {

                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)

                        if (cal.get(Calendar.AM_PM) == Calendar.AM) {
                            editor.putString("am_pm2", "am")
                            editor.commit()
                            real_hour2 = hour
                        } else {
                            editor.putString("am_pm2", "pm")
                            editor.commit()
                            real_hour2 = hour + 12
                        }
                        editor.putInt("alarm_hour2", hour)
                        editor.putInt("alarm_minute2", minute)
                        editor.putString("use_alarm2", "true")
                        editor.commit()

                        cal.apply {
                            timeInMillis = System.currentTimeMillis()
                            set(Calendar.HOUR_OF_DAY, real_hour2)
                            set(Calendar.MINUTE, minute)
                            set(Calendar.SECOND, 0)
                        }
                        alarmManager2.setInexactRepeating(
                            AlarmManager.RTC_WAKEUP,
                            cal.timeInMillis,
                            repeatInterval,
                            pendingIntent2
                        )

                        Toast.makeText(applicationContext, "알림 설정 완료", Toast.LENGTH_SHORT).show()
                        alarm_change2.visibility = View.VISIBLE
                        state2 = 1

                    }
                    if(state2 == 1){
                        add_alarm2.isChecked = true
                    }

                }

                if(state2!=1){
                    TimePickerDialog(this,  timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
                    add_alarm2.isChecked = false
                }
            }
            else{
                editor.putInt("alarm_hour2", -1)
                editor.putInt("alarm_minute2", -1)
                editor.putString("use_alarm2","false")
                editor.commit()
                alarm_change2.visibility = View.GONE
                add_alarm2.isChecked = false
                state2 = 0
                alarmManager2.cancel(pendingIntent2)
            }
        }
        alarm_change2.setOnClickListener{
            var timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                if(cal.get(Calendar.AM_PM)==Calendar.AM&&!settings.getString("am_pm2","").equals("am")) {
                    editor.putString("am_pm2", "am")
                    editor.commit()
                    count2 = 1
                    real_hour2 = hour
                }
                else if(cal.get(Calendar.AM_PM)==Calendar.PM&&!settings.getString("am_pm2","").equals("pm")){
                    editor.putString("am_pm2", "pm")
                    editor.commit()
                    count2 = 1
                    real_hour2 = hour+12
                }


                if(settings.getInt("alarm_hour2",-1) != hour){
                    editor.putInt("alarm_hour2", hour)
                    editor.commit()
                    count2 = 1
                }
                else if(settings.getInt("alarm_minute2",-1)!=minute){
                    editor.putInt("alarm_minute2", minute)
                    editor.commit()
                    count2 = 1
                }
                if(count2 == 1){
                    alarmManager2.cancel(pendingIntent2) //기존 거 취소

                    cal.apply {
                        timeInMillis = System.currentTimeMillis()
                        set(Calendar.HOUR_OF_DAY, real_hour2)
                        set(Calendar.MINUTE, minute)
                        set(Calendar.SECOND, 0)
                    }
                    alarmManager2.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        cal.timeInMillis,
                        repeatInterval,
                        pendingIntent2
                    )

                    Toast.makeText(applicationContext, "알림 수정 완료", Toast.LENGTH_SHORT).show()
                    count2 = 0
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

        }

    }


}


