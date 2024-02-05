package com.example.finalproj.todo

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.finalproj.R
import com.example.finalproj.TodoActivity
import com.example.finalproj.TodoItemEntity
import com.example.finalproj.store.Data_Theme
import kotlinx.android.synthetic.main.dialog_todo_check.view.*
import kotlinx.coroutines.*
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate
import kotlin.coroutines.CoroutineContext


//Update 다이얼로그 프래그먼트
class TodoCheckDialog : DialogFragment(), CoroutineScope {




    override val coroutineContext: CoroutineContext get() = Dispatchers.Main

    private val todoDao by lazy { (requireContext() as TodoActivity).todoDao }

    private val todoIdx by lazy {
        arguments?.getLong("TODO_KEY") ?: throw IllegalArgumentException("전달받은 Note값이 없습니다.")
    }
    private val todocontent by lazy {
        arguments?.getString("TODO_CONTENT") ?: throw IllegalArgumentException("전달받은 Note값이 없습니다.")
    }
    private val tododate by lazy {
        arguments?.getString("TODO_DATE") ?: throw IllegalArgumentException("전달받은 Note값이 없습니다.")
    }
    val todo = Todo



    //coin을 sharedpreference에 저장
    //저장된 coin값을 불러오고 누적해 저장한다.


    //checkDialog 레이아웃 inflate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return inflater.inflate(R.layout.dialog_todo_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 메인쓰레드로 런쳐 시작
        GlobalScope.launch(Dispatchers.Main) {
            //데이터 조회
            val todoResult = withContext(Dispatchers.IO) {
                todoDao.selectTodo(todoIdx) }
            //조회한 데이터를 해당 뷰에 표시
            view.content_todo.setText(todoResult!!.todoitemContent)
        }

        /* 네 버튼 누른경우 check값 true로 전환 및 코인 적립*/
        view.btn_yes.setOnClickListener {

            // update 작업을 위한 코루틴 수행
            launch(Dispatchers.Main) {
                withContext(Dispatchers.Default) {
                    todoDao.setTodocheck(
                        todoIdx,true
                    )
                    //할일을 다했을 경우 코인 증정
                   todo.updateCoin(requireActivity() as TodoActivity,100)
                }
                dismiss()//다이얼로그 종료
            }
        }
        //아니오를 누른 경우 check값 false 전환
        view.btn_no.setOnClickListener {

            // update 작업을 위한 코루틴 수행
            launch(Dispatchers.Main) {
                withContext(Dispatchers.Default) {
                    todoDao.setTodocheck(
                        todoIdx,false
                    )
                }
                dismiss()//다이얼로그 종료
            }
        }
    }
}