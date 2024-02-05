import com.example.finalproj.R

enum class Data_Snack(val itemName: String, val itemDetail: String, val price: Int, val itemImg: Int, val resultCode: Int) {
    Snack1("육포","강아지에 맞춘 저염식 육포", 20, R.drawable.item1, 101),
    Snack2("고구마 말랭이","강아지들의 최애 간식 No. 1", 25, R.drawable.item2, 102),
    Snack3("소세지", "줄 때는 꼭 껍질을 벗겨서!!!", 20, R.drawable.item3, 103),
    Snack4("견사돌", "치아 건강 관리 필수템", 60, R.drawable.item4, 104),
    Snack5("멍비어", "강아지계 맥주 (알코올 0%)", 50, R.drawable.item5, 105),
    Snack6("오리목뼈", "오독오독 오도독", 200, R.drawable.item6, 106),
    Snack7("소 간 파우더", "눈물 자국에 좋아요!", 60, R.drawable.item7, 107),
    Snack8("우유껌", "가장 기본적인 대표 간식", 15, R.drawable.item8, 108),
    Snack9("퍼피치노", "강아지용 커피 (카페인 0%)", 50, R.drawable.item9,109),
    Snack10("우유", "락토프리! 저지방 고단백 간식!", 30, R.drawable.item10, 110);
}