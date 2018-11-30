package com.example.mihir.redditcoroutine.data.repository

import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.PostDetailResponse
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class PostDetailRepository(val database: AppDatabase) {

    val oauth = RedditAPI.oauthApi

    suspend fun getDetails(accessToken: String, subreddit: String, article: String): Response<List<PostDetailResponse>> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        return oauth.getPostDetails(headers, subreddit, article).await()
    }

    suspend fun getLocalPost(id: String): PostEntity {
        return coroutineScope {
            async(Dispatchers.IO) {
                database.postDao().getPostById(id)
            }.await()
        }
    }

    suspend fun updateLocalPosts(id: String, score: Int, numComments: Int) {
        coroutineScope {
            async(Dispatchers.IO) {
                database.postDao().updateEntity(id, score, numComments)
            }.await()
        }
    }

}