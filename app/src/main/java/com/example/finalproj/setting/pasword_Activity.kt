package com.example.finalproj.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproj.LoginActivity
import com.example.finalproj.MainActivity
import com.example.finalproj.R
import com.example.finalproj.store.Data_Theme
import kotlinx.android.synthetic.main.setting_password.*

class password_Activity : AppCompatActivity() {
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
        setContentView(R.layout.setting_password)

        val settings: SharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()

        var state:String? = settings.getString("use_password","")
        if(state.equals("true")){
            password_switch.isChecked = true
            password_change.visibility = View.VISIBLE
            password_change.setOnClickListener {
                val myIntent = Intent(this, change_Activity::class.java)
                startActivity(myIntent)
            }
        }
        else{
            password_switch.isChecked = false
            password_change.visibility = View.GONE
        }

        password_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                editor.putString("use_password", true.toString())
                editor.commit()
                val myIntent = Intent(this, register_Activity::class.java)
                startActivity(myIntent)

                password_change.visibility = View.VISIBLE
                password_change.setOnClickListener {
                    val myIntent = Intent(this, change_Activity::class.java)
                    startActivity(myIntent)
                }
            }
            else{
                editor.putString("use_password", false.toString())
                editor.commit()
                password_change.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, setting_basic_Activity::class.java))
        finish()
    }

}




