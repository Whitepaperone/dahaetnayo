package com.example.finalproj.todo

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.finalproj.R
import com.example.finalproj.TodoActivity
import com.example.finalproj.TodoItemEntity
import kotlinx.android.synthetic.main.dialog_todo_create.btn_date
import kotlinx.android.synthetic.main.dialog_todo_create.btn_end_date
import kotlinx.android.synthetic.main.dialog_todo_create.edit_new_todo
import kotlinx.android.synthetic.main.dialog_todo_create.view.*
import kotlinx.android.synthetic.main.dialog_todo_create.view.btn_date
import kotlinx.android.synthetic.main.dialog_todo_create.view.btn_end_date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


//Create 다이얼로그 프래그먼트
class TodoCreateDialog() : DialogFragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    //tododate값을 전달받기 위해 companion object선언
    companion object{
        const val TODO_DATE = "tododate"

        fun newInstance(tododate: String): TodoCreateDialog {
            val fragment = TodoCreateDialog()

            val bundle = Bundle().apply{
                putString(TODO_DATE, tododate)
            }
            fragment.arguments=bundle

            return fragment
        }
    }
    var updateDate:String?=""
    var updateEndDate:String?=""
    var todofun = Todo


    // 처음 호출될때 초기화
    private val todoDao by lazy { (requireContext() as TodoActivity).todoDao }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tododate = arguments?.getString(TODO_DATE)

        // 추가 다이얼로그 위한 레이아웃 inflate
        val rootView = inflater.inflate(R.layout.dialog_todo_create, container, false)
        rootView.btn_date.setText(tododate)
        rootView.btn_end_date.setText(tododate)
        rootView.btn_date.setOnClickListener {
            val dpd = DatePickerDialog(requireContext() as TodoActivity, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                updateDate=todofun.parsePickerDate(year,monthOfYear,dayOfMonth)
                btn_date.setText(updateDate)
            }, todofun.year, todofun.month, todofun.day)
            dpd.show()
        }
        rootView.btn_end_date.setOnClickListener {
            val dpd = DatePickerDialog(requireContext() as TodoActivity, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                updateEndDate=todofun.parsePickerDate(year,monthOfYear,dayOfMonth)
                btn_end_date.setText(updateEndDate)
            }, todofun.year, todofun.month, todofun.day)
            dpd.show()
        }


        // 추가버튼 클릭시 데이터베이스에 값을 저장
        rootView.btn_new_todo.setOnClickListener {
            val date=btn_date.getText().toString()
            val enddate=btn_end_date.getText().toString()
            val day =todofun.getDday(date)
            val dday = todofun.getDday(enddate)
            if(day<=dday){
                launch(coroutineContext + Dispatchers.IO) {
                    //insert
                    createTodo(
                        TodoItemEntity(
                            todoitemContent = edit_new_todo.text.toString(),
                            todoitemDate = date,
                            todoitemEndDate = enddate,
                            todoitemChecked = false)
                    )
                    withContext(Dispatchers.Main) {

                        Toast.makeText(requireContext(), "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                        dismiss()//다이얼로그 종료
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "기간설정이 잘못되었습니다", Toast.LENGTH_SHORT).show()
                }

        }
        return rootView
    }


    // 데이터베이스에 insert 작업을 위한 suspend 함수 선언
    suspend fun createTodo(todo: TodoItemEntity) = withContext(Dispatchers.IO) {
        //insert
        Log.d("TAG", "NoteCreateDialog-createNote-insert")
        todoDao.insertTodos(todo)
    }
}