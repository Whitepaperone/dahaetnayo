package com.example.finalproj.store
import com.example.finalproj.R
enum class Data_Pet(val itemName: String, val itemDetail: String, val price: Int, val itemImg: Int, val standImg: Int, val sitImg: Int, val foodImg: Int, val waterImg: Int, val snackimg: Int) {
    Pet1("다온","새하얀 털을 가진 귀여운 친구와 함께라면 모든 좋은 일들이 다 올지도 몰라요.", 0,0,R.drawable.pet1_stand, R.drawable.pet1_sit, R.drawable.pet1_food, R.drawable.pet1_water, R.drawable.pet1_snack),
    Pet2("라온","언제나 즐거운 친구 라온이와 함께라면 즐거운 일이 가득할지도 몰라요", 2,0,R.drawable.pet2_stand, R.drawable.pet2_sit, R.drawable.pet2_food, R.drawable.pet2_water, R.drawable.pet2_snack)
}