package com.example.finalproj.setting

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproj.MainActivity
import com.example.finalproj.PetActivity
import com.example.finalproj.R
import com.example.finalproj.TodoActivity
import com.example.finalproj.store.Data_Theme
import com.example.finalproj.store.store_Activity
import com.example.finalproj.todo.Todo
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.setting_basic.*

class setting_basic_Activity : AppCompatActivity() {

    private lateinit var rewardedAd: RewardedAd
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
        setContentView(R.layout.setting_basic)
        val settings: SharedPreferences = getSharedPreferences("TODO_PREF", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()
        //코인
        val pref=getSharedPreferences("TODO_PREF",0)
        var coin = pref.getInt("KEY_COIN",0)
        coinView.text = coin.toString()
        MobileAds.initialize(this){}



        fun createAndLoadRewardedAd(): RewardedAd {
            rewardedAd = RewardedAd(this, "ca-app-pub-")
            val adLoadCallback = object: RewardedAdLoadCallback() {
                override fun onRewardedAdLoaded() {
                    // Ad successfully loaded.
                }
                override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                    // Ad failed to load.
                    Toast.makeText(applicationContext, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
            return rewardedAd
        }
        createAndLoadRewardedAd()



        setting_password.setOnClickListener {
            val myIntent = Intent(this, password_Activity::class.java)
            startActivity(myIntent)

        }
        setting_alarm.setOnClickListener {
            val myIntent = Intent(this, use_alarm_Activity::class.java)
            startActivity(myIntent)

        }




        setting_ads.setOnClickListener {

            if (rewardedAd.isLoaded) {
                val activityContext: Activity = this@setting_basic_Activity
                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        // Ad closed.
                        rewardedAd = createAndLoadRewardedAd()

                    }
                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        // User earned reward.
                        //코인 증가
                        coin += 100
                        editor.putInt("KEY_COIN", coin)
                        editor.commit()
                        coinView.text = coin.toString()
                        Toast.makeText(applicationContext, "코인이 지급되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    override fun onRewardedAdFailedToShow(adError: AdError) {
                        // Ad failed to display.
                        Toast.makeText(applicationContext, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                rewardedAd.show(activityContext, adCallback)
            }
            else {

                Toast.makeText(applicationContext, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        fabMenu.setOnClickListener {
            fabMenu.setExpanded(true)
        }
        btn_todo.setOnClickListener{
            val dateintent = Intent(this, TodoActivity::class.java)
            val todo = Todo
            val year = todo.year
            val month = todo.month
            val dayOfMonth = todo.day
            val tododate = todo.parsePickerDate(year,month,dayOfMonth)
            dateintent.putExtra(MainActivity.TODO_DATE,tododate)
            this.startActivity(dateintent)
        }
        btn_pet.setOnClickListener{
            //입력받은 날짜 TodoActivity로 전달
            val dateintent = Intent(this, PetActivity::class.java)
            this.startActivity(dateintent)
        }
        setting_store.setOnClickListener{
            val dateintent = Intent(this, store_Activity::class.java)
            this.startActivity(dateintent)
        }
        tvClose.setOnClickListener {
            fabMenu.setExpanded(false)
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
