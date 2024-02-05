package com.example.finalproj

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.finalproj.setting.setting_basic_Activity
import com.example.finalproj.store.Data_Theme
import com.example.finalproj.store.store_Activity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
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
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics

        var calendar: CalendarView?

        calendar = findViewById<View>(R.id.calendar) as CalendarView

        // Add Listener in calendar
        calendar.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { _, year, month, dayOfMonth ->
                val tododate = parsePickerDate(year,month,dayOfMonth)
                val todoyear = year
                val todomonth= month
                val tododay = dayOfMonth

                //입력받은 날짜 TodoActivity로 전달
                val dateintent =Intent(this, TodoActivity::class.java)
                dateintent.putExtra(TODO_DATE,tododate)
                this.startActivity(dateintent)
            })
        fabMenu.setOnClickListener {
            fabMenu.setExpanded(true)
        }
        go_setting.setOnClickListener{
            val dateintent = Intent(this, setting_basic_Activity::class.java)
            this.startActivity(dateintent)
        }
        btn_pet.setOnClickListener{
            //입력받은 날짜 TodoActivity로 전달
            val dateintent = Intent(this, PetActivity::class.java)
            this.startActivity(dateintent)
        }
        btn_store.setOnClickListener{
            val dateintent = Intent(this, store_Activity::class.java)
            this.startActivity(dateintent)
        }
        tvClose.setOnClickListener {
            fabMenu.setExpanded(false)
        }

    }

    companion object{
        const val TODO_DATE = "TODO_DATE"
        const val TODO_YEAR = "TODO_YEAR"
        const val TODO_MONTH = "TODO_MONTH"
        const val TODO_DAY = "TODO_DAY"

    }

    //완전복붙햇음
    fun parsePickerDate(Year: Int, Month: Int, day: Int): String {
        val sYear = Year.toString()
        var sMonth = (Month + 1).toString()
        var sDay = day.toString()
        if (sDay.length == 1) sDay = "0$sDay"
        if (sMonth.length == 1) sMonth = "0$sMonth"
        return "$sYear-$sMonth-$sDay"
    }

    //추가
    var mBackWait:Long = 0
    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - mBackWait >=1000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.finishAffinity(this)
        }
    }
}
