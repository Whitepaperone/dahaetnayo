package com.example.finalproj.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.finalproj.R
import com.example.finalproj.TodoActivity
import com.example.finalproj.TodoItemEntity
import kotlinx.android.synthetic.main.dialog_todo_create.*
import kotlinx.android.synthetic.main.dialog_todo_create.view.edit_new_todo
import kotlinx.android.synthetic.main.dialog_todo_update.*
import kotlinx.android.synthetic.main.dialog_todo_update.btn_date
import kotlinx.android.synthetic.main.dialog_todo_update.btn_end_date
import kotlinx.android.synthetic.main.dialog_todo_update.view.*
import kotlinx.android.synthetic.main.dialog_todo_update.view.btn_date
import kotlinx.android.synthetic.main.list_item_todo.*
import kotlinx.android.synthetic.main.list_item_todo.view.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

//Update 다이얼로그 프래그먼트
class TodoUpdateDialog : DialogFragment(), CoroutineScope {


    override val coroutineContext: CoroutineContext get() = Dispatchers.Main

    private val todoDao by lazy { (requireContext() as TodoActivity).todoDao }

    private val todoIdx by lazy {
        arguments?.getLong("TODO_KEY") ?: throw IllegalArgumentException("전달받은 투두값이 없습니다.")
    }

    var updateDate: String? = ""
    var updateEndDate: String? = ""
    var todofun = Todo


    //추가 다이얼로그 위한 레이아웃 inflate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_todo_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //메인쓰레드로 런쳐를 시작
        GlobalScope.launch(Dispatchers.Main) {
            //데이터 조회
            val todoResult = withContext(Dispatchers.IO) {
                todoDao.selectTodo(todoIdx)
            }
            //조회한 데이터를 해당 뷰에 표시
            view.edit_new_todo.setText(todoResult!!.todoitemContent)
            view.btn_date.setText(todoResult!!.todoitemDate)
            view.btn_end_date.setText(todoResult!!.todoitemEndDate)
            updateDate = todoResult!!.todoitemDate
        }

        //날짜수정
        view.btn_date.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext() as TodoActivity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    updateDate = todofun.parsePickerDate(year, monthOfYear, dayOfMonth)
                    btn_date.setText(updateDate)
                },
                todofun.year,
                todofun.month,
                todofun.day
            )
            dpd.show()
        }
        view.btn_end_date.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext() as TodoActivity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    updateEndDate = todofun.parsePickerDate(year, monthOfYear, dayOfMonth)
                    btn_end_date.setText(updateEndDate)
                },
                todofun.year,
                todofun.month,
                todofun.day
            )
            dpd.show()
        }


        //수정 버튼 누른경우
        view.btn_update_todo.setOnClickListener {
            // update 작업을 위한 코루틴 수행
            //update되었을때 힐 일이 바뀌었다 가정하고 체크를 초기화
            val date = btn_date.getText().toString()
            val enddate = btn_end_date.getText().toString()
            val day = todofun.getDday(date)
            val dday = todofun.getDday(enddate)
            if (day <= dday) {
                launch(coroutineContext + Dispatchers.IO) {
                    //insert
                    todoDao.updateTodoItems(
                        TodoItemEntity(
                            todoitemId = todoIdx,
                            todoitemContent = view.edit_new_todo.text.toString(),
                            todoitemDate = date,
                            todoitemEndDate = enddate,
                            todoflag = false,
                            todoitemChecked = false
                        )
                    )
                    withContext(Dispatchers.Main) {

                        Toast.makeText(requireContext(), "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                        dismiss()//다이얼로그 종료
                    }
                }
            } else {
                Toast.makeText(requireContext(), "기간설정이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}