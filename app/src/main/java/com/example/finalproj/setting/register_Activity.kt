package com.example.finalproj.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproj.LoginActivity
import com.example.finalproj.MainActivity

import com.example.finalproj.R
import com.example.finalproj.store.Data_Theme
import kotlinx.android.synthetic.main.login.*

class register_Activity : AppCompatActivity(){
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
        setContentView(R.layout.login)
        val settings: SharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()

        var password:String = ""
        var double_check:String = ""
        var count: Int = 0
        var step: Int = 1

        fun make_password(num:String){
            password += num
            count++
        }
        fun make_double_check(num:String){
            double_check += num
            count++
        }
        fun box(count:Int, password:String){
            if(count == 0){
                passcode_1.setText("")
                passcode_2.setText("")
                passcode_3.setText("")
                passcode_4.setText("")
            }
            else if(count == 1){
                passcode_1.setText((password[0]).toString())
                passcode_2.setText("")
                passcode_3.setText("")
                passcode_4.setText("")
            }
            else if(count == 2){
                passcode_1.setText((password[0]).toString())
                passcode_2.setText((password[1]).toString())
                passcode_3.setText("")
                passcode_4.setText("")
            }
            else if(count == 3){
                passcode_1.setText((password[0]).toString())
                passcode_2.setText((password[1]).toString())
                passcode_3.setText((password[2]).toString())
                passcode_4.setText("")
            }
            else{
                passcode_1.setText((password[0]).toString())
                passcode_2.setText((password[1]).toString())
                passcode_3.setText((password[2]).toString())
                passcode_4.setText((password[3]).toString())
            }

        }

        fun check(){
            if(password.length == 4&&password.equals(double_check)){
                editor.putString("passwd", double_check)
                editor.commit()
                Toast.makeText(applicationContext, "비밀번호가 설정되었습니다.", Toast.LENGTH_SHORT).show()

                val myIntent = Intent(this, password_Activity::class.java)
                startActivity(myIntent)

            }
            else if(password.length == 4&&step==1){
                box(count, password)
//text바꾸고 step++
                Handler().postDelayed({
                    count = 0
                    step++
                    tv_message.setText("다시 입력해주세요")
                    box(count, "")
                }, 200L)
            }
            else if(double_check.length == 4&& step == 2){
                Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    double_check=""
                    step = 1
                    password=""
                    count = 0
                    box(count, password)
                    tv_message.setText("비밀번호를 입력해주세요")
                }, 200L)

            }
        }



        button1.setOnClickListener{
            if(password.length<4&&step == 1){
                make_password("1")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("1")
                box(count, double_check)
            }
            check()
        }
        button2.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("2")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("2")
                box(count, double_check)
            }
            check()
        }
        button3.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("3")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("3")
                box(count, double_check)
            }
            check()
        }
        button4.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("4")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("4")
                box(count, double_check)
            }
            check()
        }
        button5.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("5")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("5")
                box(count, double_check)
            }
            check()
        }
        button6.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("6")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("6")
                box(count, double_check)
            }
            check()
        }
        button7.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("7")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("7")
                box(count, double_check)
            }
            check()
        }
        button8.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("8")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("8")
                box(count, double_check)
            }
            check()
        }
        button9.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("9")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("9")
                box(count, double_check)
            }
            check()
        }
        button0.setOnClickListener {
            if(password.length<4&&step == 1){
                make_password("0")
                box(count, password)
            }
            else if(double_check.length<4&&step==2){
                make_double_check("0")
                box(count, double_check)
            }
            check()

        }
        button_clear.setOnClickListener {
            if(step==1){
                password = ""
                count = 0
                box(count, password)
            }
            else if(step==2){
                double_check = ""
                count = 0
                box(count, double_check)
            }

        }
        button_erase.setOnClickListener {
            if(step==1){
                val len = password.length-2
                var tem = password
                password = ""
                for(i in 0..len){
                    password += tem[i]
                }
                count--
                box(count, password)
            }
            else if(step == 2){
                val len = double_check.length-2
                var tem = double_check
                double_check = ""
                for(i in 0..len){
                    double_check += tem[i]
                }
                count--
                box(count, double_check)
            }

        }

    }


}