package com.example.mihir.redditcoroutine.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comments: List<CommentEntity>)

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY position")
    fun posts(postId: String): DataSource.Factory<Int, CommentEntity>

    @Query("UPDATE comments SET position = (:listSize+position-1) WHERE postId= :postId AND position > :addPosition")
    fun updatePosition(addPosition: Int, listSize: Int, postId: String)

    @Query("DELETE FROM comments WHERE postId= :postId AND position = :deletePosition")
    fun deleteByPosition(deletePosition: Int, postId: String)

    @Query("DELETE FROM comments WHERE postId= :postId")
    fun deleteByPost(postId: String)

    @Query("DELETE FROM comments")
    fun deleteAll()
}