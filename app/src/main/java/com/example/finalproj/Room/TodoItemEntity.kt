package com.example.finalproj
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.collections.ArrayList

@Entity(tableName = "TodoItemEntity")
data class TodoItemEntity(
    @PrimaryKey (autoGenerate = true)
    var todoitemId: Long? = null,
    var todoitemContent: String?, //Todo 내용
    var todoitemDate: String?, //입력받은날짜
    var todoitemEndDate: String?,
    var todoflag:Boolean = false,
    var todoitemChecked: Boolean = false //체크박스

)
