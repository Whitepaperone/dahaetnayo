package com.example.finalproj.todo

import android.content.Context
import android.content.SharedPreferences
import com.example.finalproj.TodoActivity
import java.util.*


object Todo{
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)


    fun getDday(tododate: String): Long {
        // D-day 설정
        val ONE_DAY = 24 * 60 * 60 * 1000
        val today: Long = Calendar.getInstance().getTimeInMillis() / ONE_DAY
        val date = tododate
        val values = date.split("-").toTypedArray()

        val year = values[0].toInt()
        val month = values[1].toInt()-1
        val day = values[2].toInt()

        val ddayCalendar: Calendar = Calendar.getInstance()
        ddayCalendar.set(year, month, day)

        // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
        val dday: Long = ddayCalendar.getTimeInMillis() / ONE_DAY

        var result = dday - today

        return result
        // 출력 시 d-day 에 맞게 표시
    }

    fun Ddayformatter(tododate: String):String{
        var result = getDday(tododate)
        val strFormat: String
        if (result > 0) {
            strFormat = "D-%d"
        } else if (result == 0L) {
            strFormat = "D-Day"
        } else {
            result *= -1
            strFormat = "D+%d"
        }
        return String.format(strFormat, result)
    }
    fun parsePickerDate(Year: Int, Month: Int, day: Int): String {
        val sYear = Year.toString()
        var sMonth = (Month + 1).toString()
        var sDay = day.toString()
        if (sDay.length == 1) sDay = "0$sDay"
        if (sMonth.length == 1) sMonth = "0$sMonth"
        return "$sYear-$sMonth-$sDay"
    }

    fun updateHeart(context: Context,progress:Int){
        val pref: SharedPreferences =
            context.getSharedPreferences("TODO_PREF",0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        var heart=pref.getInt("KEY_HEART",50)
        heart = heart?.plus(progress)
        if(heart>100){ heart=100}
        if(heart<0) heart=0
        val editor=pref?.edit()
        // 키와 밸류를 쌍으로 저장하고 apply한다
        editor?.putInt("KEY_HEART",heart!!)
            ?.apply()
    }
    fun updateHungry(context: Context,progress:Int){
        val pref: SharedPreferences =
        context.getSharedPreferences("TODO_PREF",0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        var hungry=pref.getInt("KEY_HUNGRY",50)
        hungry = hungry?.plus(progress)
        if(hungry>100){ hungry=100}
        if(hungry<0) hungry=0

        val editor=pref?.edit()
        // 키와 밸류를 쌍으로 저장하고 apply한다
        editor?.putInt("KEY_HUNGRY",hungry!!)
            ?.apply()
    }
    fun updateCoin(context: Context, coins:Int){
        val pref: SharedPreferences =
            context.getSharedPreferences("TODO_PREF",0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        var coin=pref?.getInt("KEY_COIN",0)
        coin = coin?.plus(coins)

        val editor=pref?.edit()
        // 키와 밸류를 쌍으로 저장하고 apply한다
        editor?.putInt("KEY_COIN",coin!!)
            ?.apply()
    }

}