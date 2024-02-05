package com.example.finalproj

import Data_Snack
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_store.view.*
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

//간식 보유 개수 리스트를 위한 Adapter
class SnackAdapter(val context: Context, override val coroutineContext: CoroutineContext) : RecyclerView.Adapter<SnackAdapter.SnackViewHolder>(),
    CoroutineScope {

    // 리사이클러 뷰에 보여질 아이템 뷰 개수를 가져온다.
    override fun getItemCount(): Int {
        Log.d("ITEM", "getItemCount() 호출")
        return Data_Snack.values().size
    }//end of SnackAdapter

    //뷰홀더 생성 함수
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): SnackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_store, parent, false)

        return SnackViewHolder(view)
    }

    //뷰홀더 바인드 함수
    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
        holder.bindItemData(Data_Snack.values()[position])
    }

    //아이템 뷰에 들어가야할 내용 정의
    inner class SnackViewHolder(snackView: View) : RecyclerView.ViewHolder(snackView){
        private val snackName: TextView = snackView.tv_item_name
        private val snackDetail: TextView = snackView.tv_detail_info
        private val have: TextView= snackView.tv_item_price
        private val snackImg: ImageView = snackView.tv_item_image
        private val btn_use = snackView.btn_buy
        private val won = itemView.tv_item_coin

        private val snackPref: SharedPreferences = snackView.context.getSharedPreferences("SNACK_PREF", Context.MODE_PRIVATE)
        private val snack_editor: SharedPreferences.Editor = snackPref.edit()

        private val activity: SnackActivity = context as SnackActivity

        fun finishRV() = activity.finish()


        //아이템 바인드 함수
        fun bindItemData(snackData: Data_Snack){ //snack: ItemEntity){
            var having: Int = snackPref.getInt(snackData.itemName, 0)

            snackName.text = snackData.itemName
            snackDetail.text = snackData.itemDetail
            snackImg.setImageResource(snackData.itemImg)
            have.text = having.toString()
            btn_use.text="적용"
            won.text="개"

            //각 아이템 뷰의 버튼이 눌렸을 때 동작 정의.
            itemView.btn_buy.setOnClickListener {
                if(having == 0) {
                    Toast.makeText(context, "가지고 있는 간식이 없습니다!", Toast.LENGTH_SHORT).show()
                } else {
                    having -= 1
                    snack_editor.putInt(snackData.itemName, having)
                    snack_editor.commit()

                    val intent = Intent(context, SnackActivity::class.java)
                    activity.setResult(snackData.resultCode, intent)
                    finishRV()
                }
            }//end of btn_buy.setOnClickListener
        }//end of bindItemData
    }//end of SnackViewHoler
}//end of SnackAdapter