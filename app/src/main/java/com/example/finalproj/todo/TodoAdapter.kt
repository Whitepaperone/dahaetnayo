package com.example.finalproj.todo

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproj.R
import com.example.finalproj.TodoActivity
import com.example.finalproj.TodoDao
import com.example.finalproj.TodoItemEntity
import kotlinx.android.synthetic.main.list_item_todo.view.*


/* TodoAdapter 선언
  전달된 PagedList와 기존 old PagedList를 비교해 중복Page List는 보여주지 않음
  (DiffUtil로 기준 선언)
 */
class TodoAdapter(val todoDao: TodoDao, val tododate: String, private val todoActivity: TodoActivity,
                  val deleteCallback: (todo: TodoItemEntity) -> Unit) :
    PagedListAdapter<TodoItemEntity, TodoAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {


    //뷰홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //뷰홀더에 데이터를 바인딩하는 bindItems() 메서드 호출
        holder.bindItems(getItem(position))

    }
    var todofun = Todo


    //뷰홀더 생성 및 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo, parent, false)
        //뷰를 데이터와 맵핑해 뷰홀더를 반환
        return ItemViewHolder(itemView)
    }
    

    //itemView매핑
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //데이터 바인딩
        fun bindItems(todo: TodoItemEntity?) {
            //todo가 있을때
            todo?.let {

                val date = todofun.Ddayformatter(it.todoitemEndDate.toString())
                itemView.txt_todo_content.text = it.todoitemContent
                itemView.tv_dday.setText(date)
                itemView.tv_todo_end_date.text=it.todoitemEndDate
                itemView.tv_todo_date.text=it.todoitemDate

                //체크박스를 클릭할때 TodoCheckDialog로 ID,Content,Date값을 넘김
                itemView.iv_dday.setOnClickListener{_->
                    val checktodo = TodoCheckDialog().apply {
                        arguments = Bundle().apply {
                            putLong("TODO_KEY", it.todoitemId!!)
                            putString("TODO_CONTENT",it.todoitemContent)
                            }
                    }

                    //CheckDialog출력
                    checktodo.show(
                        (itemView.context as AppCompatActivity).supportFragmentManager,
                        null
                    )
                }

                //todoitemChecked가 true 일시 체크박스가 체크됨

                //완료됐을시 dday구역 색변화
                if(!it.todoitemChecked) {
                    itemView.iv_dday.setBackgroundColor(Color.WHITE)
                }
                //지정된 기간내에 투두완료를 못할시 애정도 깎기
                val dday = todofun.getDday(it.todoitemEndDate.toString())
                if(!it.todoitemChecked && !it.todoflag && dday<0) {
                    todoActivity.setflag(it.todoitemId!!)
                    todofun.updateHeart(todoActivity,-10)
                    todofun.updateHungry(todoActivity,-10)
                    todofun.updateCoin(todoActivity,-100)
                    Toast.makeText(todoActivity, "기간내 완수하지 못한 일정이 있습니다! 의욕도가 감소됩니다", Toast.LENGTH_SHORT).show()
                }
                if(dday<0){
                    itemView.txt_todo_content.setPaintFlags(itemView.txt_todo_content.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                }




                //수정 이미지뷰를 선택했을 때 TodoUpdateDialog 출력
                itemView.iv_todo_edit.setOnClickListener { _ ->
                    Log.d("TAG", "ItemViewHolder-수정버튼 클릭시 ")
                    //수정 다이얼로그 생성
                    val updatedate = TodoUpdateDialog().apply {
                        arguments = Bundle().apply {
                            putLong("TODO_KEY", it.todoitemId!!)}
                    }

                    //수정 다이얼로그 출력
                    updatedate.show(
                        (itemView.context as AppCompatActivity).supportFragmentManager,
                        null
                    )
                }

                //삭제 이미지뷰 클릭시 callback함수로 deleteTodoItems 실행
                itemView.iv_todo_delete.setOnClickListener { _ ->
                    Log.d("TAG", "ItemViewHolder-삭제버튼 클릭시 ")
                    //삭제할 TodoEntity를 전달하여 삭제 위임
                    deleteCallback(it)
                }
            }
        }
    }


    /*  RecyclerView DiffUtil 클래스 구현해 두 목록간의 차이를 찾아 비교한후 새로운 페이지로 갱신
       - areItemsTheSame() : 두 객체가 같은 항목인지 여부를 결정
       - areContentsTheSame() : TodoEntity의 데이터 항목이 같은지 여부를 결정
     */
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TodoItemEntity>() {
            override fun areItemsTheSame(oldConcert: TodoItemEntity, newConcert: TodoItemEntity): Boolean =
                oldConcert.todoitemId == newConcert.todoitemId

            override fun areContentsTheSame(oldConcert: TodoItemEntity, newConcert: TodoItemEntity): Boolean =
                oldConcert == newConcert

        }
    }
}