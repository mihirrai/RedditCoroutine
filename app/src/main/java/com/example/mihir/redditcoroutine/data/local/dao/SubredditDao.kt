package com.example.mihir.redditcoroutine.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity

@Dao
interface SubredditDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(posts: List<SubredditEntity>)

    @Query("SELECT * FROM subreddits WHERE refreshToken = :refreshToken")
    fun subreddits(refreshToken: String): DataSource.Factory<Int, SubredditEntity>

    @Query("DELETE FROM subreddits")
    fun deleteAll()
}