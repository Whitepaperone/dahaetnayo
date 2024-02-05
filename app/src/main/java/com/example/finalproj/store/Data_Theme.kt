package com.example.finalproj.store

import com.example.finalproj.R


enum class Data_Theme(val itemKEY: String, val itemName: String, val itemDetail: String, val itemImg: Int, val price: Int) {
    Theme1("Basic", "특별한 너에게", "이건 공짜랍니다", R.drawable.theme_basic,0),
    Theme2("Icecream", "데이지 꽃다발", "봄의 나라 이야기", R.drawable.theme_icecream,1),
    Theme3("Red", "열정의 뜨거움", "뜨거운 사랑을 그대에게", R.drawable.theme_red, 1),
    Theme4("Pink", "핑크 곤듀", "나의 세계로 놀러오세요", R.drawable.theme_pink, 1),
    Theme5("Yellow", "가을의 지뢰", "발밑의 은행을 조심하세요", R.drawable.theme_yellow, 1),
    Theme6("Green", "팅커벨의 여행", "나랑 피터팬 보러 갈래?", R.drawable.theme_green,1),
    Theme7("Sea", "바다의 향기", "인어 공주가 반겨주는 여기는 안다다씨", R.drawable.theme_sea,1),
    Theme8("Blue", "꽃보다 바다", "하얀 천이랑 바람만 있으면 어디든 갈 수 있어", R.drawable.theme_blue,1),
    Theme9("Purple", "라벤더의 인사","나의 향기에 취해보세요", R.drawable.theme_purple,1),
    Theme10("violet", "보라빛 추억","기억해? 복도에서 떠들다 같이 혼나던 우리 둘", R.drawable.theme_violet,1),
    Theme11("coffee", "라떼는 말이야","라떼 is horse", R.drawable.theme_coffee, 1)
}