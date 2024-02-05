@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.finalproj
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproj.Room.AppDB
import com.example.finalproj.store.Data_Theme
import com.example.finalproj.todo.TodoAdapter
import com.example.finalproj.todo.TodoCreateDialog
import kotlinx.android.synthetic.main.activity_pet.*
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.activity_todo.coinView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import java.util.*


class TodoActivity : AppCompatActivity(), CoroutineScope {



    //coin을 sharedpreference에 저장
    //저장된 coin값을 불러오고 0원이 아닐때 코인뷰에 모인 금액표시

    val todoDao by lazy { AppDB.getDB(this).todoDao() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    fun setflag (todoid:Long){
        launch(coroutineContext + Dispatchers.IO) {
            todoDao.setTodoflag(todoid,true)
        }
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
        changeTheme()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_todo)
        //캘린더에서 넘어온 날짜 값 저장
        val tododate=intent.getStringExtra(MainActivity.TODO_DATE)
        val pref=getSharedPreferences("TODO_PREF",0)
        // 키에 해당되는 밸류를 가져오는데 저장된 값이 없으면 0을 가져온다
        val coin=pref.getInt("KEY_COIN",0)
        coinView.setText(coin.toString())


        // TodoAdapter생성 및 deleteCallback(it)을 통해 삭제할 코드를 람다식으로 작성
        val todoAdapter = TodoAdapter(todoDao,tododate,this) { todo ->
            Log.d("TAG", "MainActivity-delete")

            //투두 삭제
            launch(coroutineContext + Dispatchers.IO) {
                todoDao.deleteTodoItems(todo)
            }
        }

        //리사이클러뷰 초기화
        recycle_todo.adapter = todoAdapter
        recycle_todo.layoutManager = LinearLayoutManager(this)


        // PageList 생성하rh 데이터의 변동사항이 있을때 계속 데이터를 갱신
        val getDataFromDB: LiveData<PagedList<TodoItemEntity>> = LivePagedListBuilder(todoDao.selectTodos(), 20).build()

        // 데이터 변경시 자동 호출됨
        //LiveData로부터 변경된 값을 Adapter에 전달
        getDataFromDB.observe(this, Observer {
            Log.d("TAG", "MainActivity-observe()")

            //변경된 pageList를 PagedAdapter에 전달
            //기존에 PagedList가 존재하면 그 차이를 비교한 후 리사이클러뷰를 새로운 페이지로 갱신

            todoAdapter.submitList(it)
            coinView.setText(coin.toString())
        })



        //todoCreateDialog 출력
        fab_add_todo.setOnClickListener {
            Log.d("TAG", "MainActivity-NoteCreateDialog()")

            //TodoCreateDialog로 tododate값 전달
            val createdate = TodoCreateDialog.newInstance(tododate)
            createdate.show(supportFragmentManager, null)
        }
    }

}