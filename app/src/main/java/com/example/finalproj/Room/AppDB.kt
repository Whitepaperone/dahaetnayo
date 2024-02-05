package com.example.finalproj.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproj.TodoDao
import com.example.finalproj.TodoItemEntity

//TODO-DB
@Database(entities = (arrayOf(TodoItemEntity::class)),version = 6)
abstract class AppDB: RoomDatabase(){
    abstract fun todoDao(): TodoDao

    companion object{
        private var database: AppDB? = null

        private const val ROOM_DB = "todo.db"

        fun getDB(context: Context): AppDB {
            if(database ==null){
                database = Room.databaseBuilder((
                        context.applicationContext),
                    AppDB::class.java, ROOM_DB
                ).fallbackToDestructiveMigration().build()
            }
            return database!!
        }
    }
}
