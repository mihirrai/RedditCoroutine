package com.example.mihir.redditcoroutine.data.repository

import android.util.Log.d
import androidx.paging.DataSource
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.SubredditListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class SubredditRepository(val database: AppDatabase) {

    val oauth = RedditAPI.oauthApi

    fun getSubreddits(refreshToken: String): DataSource.Factory<Int, SubredditEntity> {
        return database.subredditDao().subreddits(refreshToken)
    }

    suspend fun getRemoteSubreddits(accessToken: String, scope: String, after: String): Response<SubredditListResponse> {
        val headers = HashMap<String, String>()
        val options = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        options["after"] = after
        d("scope", (scope == "*").toString())
        return if (scope == "*")
            oauth.getDefaultSubreddits(headers, options).await()
        else
            oauth.getSubredditList(headers, options).await()
    }


    suspend fun saveSubreddits(body: SubredditListResponse, refreshToken: String) = coroutineScope {
        async(Dispatchers.IO) {
            val subreddits = body.data.children.map { children -> SubredditEntity(children.data.name, children.data.displayName, refreshToken,children.data.iconImg) }
            database.subredditDao().insert(subreddits)
        }.await()
    }
}