package com.example.mihir.redditcoroutine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_details")
data class PostDetailEntity(
        @PrimaryKey
        val id: String,
        val author: String,
        val body: String,
        val createdUtc: Int,
        val parent: String
)