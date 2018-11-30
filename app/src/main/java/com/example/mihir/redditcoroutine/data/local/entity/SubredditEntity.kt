package com.example.mihir.redditcoroutine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subreddits")
data class SubredditEntity(
        @PrimaryKey
        val id: String,
        val subredditName: String,
        val refreshToken: String,
        val iconUrl:String
)