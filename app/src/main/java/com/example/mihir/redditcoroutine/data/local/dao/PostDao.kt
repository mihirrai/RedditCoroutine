package com.example.mihir.redditcoroutine.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(posts: List<PostEntity>)

    @Query("UPDATE posts SET score =:newScore, numComments =:newComments WHERE id =:id")
    fun updateEntity(id: String,newScore: Int, newComments: Int)

    @Query("SELECT * FROM posts WHERE sourceSubreddit = :subreddit")
    fun posts(subreddit: String): DataSource.Factory<Int, PostEntity>

    @Query("DELETE FROM posts ")
    fun deleteAll()

    @Query("DELETE FROM posts WHERE sourceSubreddit = :subreddit")
    fun deleteBySubreddit(subreddit: String)

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: String): LiveData<PostEntity>
}