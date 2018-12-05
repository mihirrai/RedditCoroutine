package com.example.mihir.redditcoroutine.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mihir.redditcoroutine.data.local.dao.CommentDao
import com.example.mihir.redditcoroutine.data.local.dao.PostDao
import com.example.mihir.redditcoroutine.data.local.dao.SubredditDao
import com.example.mihir.redditcoroutine.data.local.dao.TokenDao
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import com.example.mihir.redditcoroutine.data.local.entity.TokenEntity

@Database(entities = [TokenEntity::class, PostEntity::class, SubredditEntity::class, CommentEntity::class], version = 1)
@TypeConverters(TimeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao
    abstract fun postDao(): PostDao
    abstract fun subredditDao(): SubredditDao
    abstract fun commentDao(): CommentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "Sample.db")
                        .build()
    }
}