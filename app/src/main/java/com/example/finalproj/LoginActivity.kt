package com.example.finalproj

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*
import android.content.SharedPreferences
import android.os.Handler
import android.widget.Toast
import com.example.finalproj.setting.change_Activity
import com.example.finalproj.setting.register_Activity
import com.example.finalproj.store.Data_Theme


class LoginActivity : AppCompatActivity() {
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

        var password:String = ""
        var count: Int = 0
        tv_message.setText("비밀번호를 입력해주세요")
        fun make_password(num:String){
            password += num
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
            if(password.length == 4&&password.equals(settings.getString("passwd",""))){
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
            }
            else if(password.length == 4){
                box(count, password)

                Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    count = 0
                    password = ""
                    box(count, password)
                }, 200L)
            }
        }



        button1.setOnClickListener{
            if(password.length<4){
                make_password("1")
                box(count, password)
            }
            check()
        }
        button2.setOnClickListener {
            if(password.length<4){
                make_password("2")
                box(count, password)
            }
            check()
        }
        button3.setOnClickListener {
            if(password.length<4){
                make_password("3")
                box(count, password)
            }
            check()
        }
        button4.setOnClickListener {
            if(password.length<4){
                make_password("4")
                box(count, password)
            }
            check()
        }
        button5.setOnClickListener {
            if(password.length<4){
                make_password("5")
                box(count, password)
            }
            check()
        }
        button6.setOnClickListener {
            if(password.length<4){
                make_password("6")
                box(count, password)
            }
            check()
        }
        button7.setOnClickListener {
            if(password.length<4){
                make_password("7")
                box(count, password)
            }
            check()
        }
        button8.setOnClickListener {
            if(password.length<4){
                make_password("8")
                box(count, password)
            }
            check()
        }
        button9.setOnClickListener {
            if(password.length<4){
                make_password("9")
                box(count, password)
            }
            check()
        }
        button0.setOnClickListener {
            if(password.length<4){
                make_password("0")
                box(count, password)
            }
            check()

        }
        button_clear.setOnClickListener {
            password = ""
            count = 0
            box(count, password)
        }
        button_erase.setOnClickListener {
            val len = password.length-2
            var tem = password
            password = ""
            for(i in 0..len){
                password += tem[i]
            }
            count--
            box(count, password)
        }

    }


}
