package com.example.mihir.redditcoroutine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
        @PrimaryKey
        val id: String,
        val position:Int,
        val author: String?,
        val body: String?,
        val createdUtc: Int?,
        val parent: String,
        val postId:String
)