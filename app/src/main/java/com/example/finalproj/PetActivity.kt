package com.example.finalproj

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.finalproj.store.Data_Pet
import com.example.finalproj.store.Data_Theme
import kotlinx.android.synthetic.main.activity_pet.*


class PetActivity: AppCompatActivity() {

    fun changeTheme(){
        val themepref=this.getSharedPreferences("STORE_PREF", 0)

        if(themepref.getInt(Data_Theme.Theme1.itemName, 0).equals(2)) {
            setTheme(R.style.Basic)
           // lineTheme.setBackgroundColor(resources.getResources().getColor .getColor(R.style.Basic))
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
    }//end of changeTheme

    private fun loadData() {
        val pref = getSharedPreferences("TODO_PREF", 0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        val coin = pref.getInt("KEY_COIN", 0)
        val heart = pref.getInt("KEY_HEART", 50)
        val hungry = pref.getInt("KEY_HUNGRY", 50)
        coinView.setText(coin.toString())
        hungry_progress.setProgress(hungry)
        tv_hungry.setText(hungry.toString())
        heart_progress.setProgress(heart)
        tv_heart.setText(heart.toString())
    }
    private fun updateHeart(progress: Int) {
        val pref = getSharedPreferences("TODO_PREF", 0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        var heart = pref.getInt("KEY_HEART", 50)
        heart = heart?.plus(progress)
        val editor = pref?.edit()
        // 키와 밸류를 쌍으로 저장하고 apply한다
        editor?.putInt("KEY_HEART", heart!!)
            ?.apply()
    }
    private fun updateHungry(progress: Int) {
        val pref = getSharedPreferences("TODO_PREF", 0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        var hungry = pref.getInt("KEY_HUNGRY", 50)
        hungry = hungry?.plus(progress)
        val editor = pref?.edit()
        // 키와 밸류를 쌍으로 저장하고 apply한다
        editor?.putInt("KEY_HUNGRY", hungry!!)
            ?.apply()
    }
    private fun setPet(): String {
        val pref = getSharedPreferences("STORE_PREF", Context.MODE_PRIVATE)
        val pet1 = pref.getInt(Data_Pet.Pet1.itemName, 0)
        val pet2 = pref.getInt(Data_Pet.Pet2.itemName, 0)
        var currentPet = Data_Pet.Pet1.itemName
        if (pet1 == 2) currentPet = Data_Pet.Pet1.itemName else if (pet2 == 2) currentPet = Data_Pet.Pet2.itemName
        return currentPet
    }//end of setPet

    private fun standGif(itemName: String, p: ImageView){
        var img: Int = 0 //이미지는 Int 타입
        when (itemName) {
            Data_Pet.Pet1.itemName -> img = Data_Pet.Pet1.standImg
            Data_Pet.Pet2.itemName -> img = Data_Pet.Pet2.standImg
        }
        Glide.with(this).load(img).into(p)
    }//end of standGif

    private fun foodGif(itemName: String, p: ImageView) {
        var img: Int = 0
        when (itemName) {
            Data_Pet.Pet1.itemName -> img = Data_Pet.Pet1.foodImg
            Data_Pet.Pet2.itemName -> img = Data_Pet.Pet2.foodImg
        }
        Glide.with(this).load(img).into(p)
    }//end of foodGif

    private fun waterGif(itemName: String, p: ImageView) {
        var img: Int = 0
        when (itemName) {
            Data_Pet.Pet1.itemName -> img = Data_Pet.Pet1.waterImg
            Data_Pet.Pet2.itemName -> img = Data_Pet.Pet2.waterImg
        }
        Glide.with(this).load(img).into(p)
    }//end of waterGif

    private fun sitGif(itemName: String, p: ImageView) {
        var img: Int = 0
        when (itemName) {
            Data_Pet.Pet1.itemName -> img = Data_Pet.Pet1.sitImg
            Data_Pet.Pet2.itemName -> img = Data_Pet.Pet2.sitImg
        }
        Glide.with(this).load(img).into(p)
    }//end of sitGif

    private fun snackGif(itemName: String, p: ImageView) {
        var img: Int
        var pet = Data_Pet.Pet1
        if (itemName == pet.itemName) {
            img = pet.snackimg
        } else {
            pet = Data_Pet.Pet2
            img = pet.snackimg
        }
        Glide.with(this).load(img).into(p)
    }//end of snackGif

    private fun setImgButton(){ //각 이미지 버튼 background colour 설정 및 pressed 할 때 effect 주는 함수 셋
        buttonEffect(btn_snack)
        buttonEffect(btn_food)
        buttonEffect(btn_water)
        buttonEffect(btn_love)
    }//end of setImgButton

    @SuppressLint("ClickableViewAccessibility")
    private fun buttonEffect(button: View) = button.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> { //button이 눌렸다면 img_btn_pressed로 background 바꿔야 함. 아니라면 img_btn_npressed 상태
                v.setBackgroundResource(R.drawable.img_btn_pressed)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.setBackgroundResource(R.drawable.img_btn_npressed)
                v.invalidate()
            }
        }
        false //false로 주어야 변경되지 아니함
    }//end of buttonEffect

    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme() //setContentView 이전에 함수를 호출해야 Theme 적용이 됨

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)

        setImgButton()
        //onCreate 단계에서 함수 호출해야 image button에 theme 색과 effect 적용됨.
        // setOnClickListener에 설정 시, 한번 눌리고 나서 effect와 색 적용되는 버그 발생함
        loadData()//현재 상태 불러옴 (coin의 개수, 허기도, 애정도)

        val puppy: ImageView = findViewById<ImageView>(R.id.puppy)
        val currentPet = setPet() // 현재 저장된 펫
        standGif(currentPet, puppy)

        btn_food.setOnClickListener {
         //   buttonEffect(btn_food)
            foodGif(currentPet, puppy)
            updateHungry(5)
            Toast.makeText(this, "밥을 먹였습니다. 왕!", Toast.LENGTH_SHORT).show()
            loadData()
        }
        btn_water.setOnClickListener {
         //   buttonEffect(btn_water)
            waterGif(currentPet, puppy)
            loadData()
            updateHungry(5)
            Toast.makeText(this, "급수했습니다. 아이 시원해!", Toast.LENGTH_SHORT).show()
        }
        btn_love.setOnClickListener {
          //  buttonEffect(btn_love)
            sitGif(currentPet, puppy)
            loadData()
            updateHeart(5)
            Toast.makeText(this, "쓰다듬어주었습니다. 더 쓰다듬어주세요!", Toast.LENGTH_SHORT).show()
        }
        btn_snack.setOnClickListener {
          //  buttonEffect(btn_snack)
            loadData()
            startActivityForResult(Intent(this, SnackActivity::class.java), 8001)
        }
    }
    override fun onResume() {  // After a pause OR at startup
        super.onResume()
        setPet()
        loadData()
        //Refresh your stuff here
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val currentPet = setPet() // 현재 저장된 펫
        val anim: ImageView = findViewById(R.id.puppy)


        if (requestCode == 8001) when (resultCode) {
            101 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "고기가 최고야!", Toast.LENGTH_SHORT).show()
            }
            102 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "말랭이는 꼬독꼬독하구 달아서 좋아", Toast.LENGTH_SHORT).show()
            }
            103 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "말랑한 소세지는 육포랑 다른 매력이 있어!", Toast.LENGTH_SHORT).show()
            }
            104 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "견사돌 먹고 잇몸 튼튼!", Toast.LENGTH_SHORT).show()
            }
            105 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "알콜은 없지만 나는 지금 취해있지", Toast.LENGTH_SHORT).show()
            }
            106 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "오리목뼈는 오도독", Toast.LENGTH_SHORT).show()
            }
            107 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "소 간 파우더 먹고 눈물자국을 지웠어!", Toast.LENGTH_SHORT).show()
            }
            108 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "우유껌이랑 우유의 차이는 뭘까?", Toast.LENGTH_SHORT).show()
            }
            109 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "거품이 몽글몽글", Toast.LENGTH_SHORT).show()
            }
            110 -> {
                updateHeart(5)
                snackGif(currentPet, anim)
                Toast.makeText(this, "우유가 최고야 >_<", Toast.LENGTH_SHORT).show()
            }
        }
    }//end of onActivityResult
}