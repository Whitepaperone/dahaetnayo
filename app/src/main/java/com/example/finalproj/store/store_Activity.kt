package com.example.finalproj.store

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.finalproj.MainActivity
import com.example.finalproj.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_store.coinView
import store_snack_Fragment

class store_Activity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    fun loadData(){
        val pref=this.getSharedPreferences("TODO_PREF",0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        val coin=pref.getInt("KEY_COIN",0)

        coinView.text = coin.toString()

    }

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
        super.onCreate(savedInstanceState)
        changeTheme()
        setContentView(R.layout.store_snack)
        loadData()
        tabLayout = findViewById<TabLayout>(R.id.store_tab)
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager!!.adapter = MyAdapter(supportFragmentManager)
        tabLayout!!.post { tabLayout!!.setupWithViewPager(viewPager) }

    }
    override fun onResume() {  // After a pause OR at startup

        super.onResume()
        loadData()
        //Refresh your stuff here
    }

    private inner class MyAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val int_items = 3;

        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = store_snack_Fragment()
                1 -> fragment = store_theme_Fragment()
                2 -> fragment = store_pet_Fragment()
            }
            return fragment!!
        }

        override fun getCount(): Int {
            return int_items
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "간식"
                1 -> return "테마"
                2 -> return "강아지"

            }
            return null
        }

    }

    override fun onBackPressed() {
        val storepref:SharedPreferences = getSharedPreferences("STORE_PREF",0)
        val store_editor: SharedPreferences.Editor = storepref.edit()

        if(storepref.getInt("use_theme", 0) == 1 || storepref.getInt("use_pet", 0)==1){
            store_editor.putInt("use_theme", 0)
            store_editor.putInt("use_pet", 0)
            store_editor.commit()
            finishAffinity()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            System.exit(0)
        }
        else{
            finish()
        }
    }



}
