package com.example.mihir.redditcoroutine.data.local.entity

import androidx.room.Entity

@Entity(tableName = "posts",primaryKeys = ["id","sourceSubreddit"])
data class PostEntity(
        val id: String,
        val title: String,
        val author: String,
        val score: Int,
        val subreddit: String,
        val numComments: Int,
        val createdUtc: Int,
        val nsfw: Boolean,
        val flair: String?,
        val gilded: Int,
        val sourceSubreddit: String,
        val thumbnailUrl:String?,
        val url:String,
        val selftext: String?,
        val isSelf:Boolean
)