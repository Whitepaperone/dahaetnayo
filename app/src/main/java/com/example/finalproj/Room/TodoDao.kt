

package com.example.finalproj

import androidx.paging.DataSource
import androidx.room.*


@Dao
interface TodoDao{

    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodos(vararg todos: TodoItemEntity)

    //Query문으로 넘긴 값을 room 이 자동으로 객체매핑
    @Query("SELECT* FROM TodoItemEntity WHERE todoitemId= :todoitemId")
    fun selectTodo(todoitemId: Long): TodoItemEntity?

  //날짜 순으로 PageList 생성
    @Query("SELECT * FROM TodoItemEntity ORDER BY todoitemChecked, todoitemEndDate")
    fun selectTodos(): DataSource.Factory<Int, TodoItemEntity>
    //위와 동일 메인용
    @Query("SELECT todoitemId,todoitemEndDate,todoitemContent,todoitemChecked FROM TodoItemEntity ORDER BY todoitemChecked")
    fun todoDday(): DataSource.Factory<Int, TodoItemMain>

    //Todo 업데이트
    @Update
    fun updateTodoItems(vararg todos: TodoItemEntity)


    @Query("UPDATE TodoItemEntity SET todoflag= :flag WHERE todoitemId = :todoitemId")
    fun setTodoflag(todoitemId: Long, flag: Boolean)

    @Query("UPDATE TodoItemEntity SET todoitemChecked= :flag WHERE todoitemId = :todoitemId")
    fun setTodocheck(todoitemId: Long?, flag: Boolean)

    @Query("UPDATE TodoItemEntity SET todoitemDate= :date WHERE todoitemId = :todoitemId")
    fun setTododate(todoitemId: Long, date: String)

    @Query("UPDATE TodoItemEntity SET todoitemEndDate= :date WHERE todoitemId = :todoitemId")
    fun setTodoEnddate(todoitemId: Long, date: String)

    @Query("UPDATE TodoItemEntity SET todoitemContent= :content WHERE todoitemId = :todoitemId")
    fun setTodocontent(todoitemId: Long, content: String)



    //Todo 삭제
    @Delete
    fun deleteTodoItems(vararg  todos: TodoItemEntity)


}
