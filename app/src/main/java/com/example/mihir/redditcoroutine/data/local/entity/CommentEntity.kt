package com.example.mihir.redditcoroutine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
        @PrimaryKey
        val id: String,
        val author: String?,
        val body: String?,
        val children: String?,
        val count: Int,
        val createdUtc: Int?,
        val depth: Int,
        val parent: String,
        val position: Int,
        val postId: String,
        val score: Int
)