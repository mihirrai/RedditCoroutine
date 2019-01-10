package com.example.mihir.redditcoroutine.data.repository

import androidx.paging.DataSource
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.response.SubredditListResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class SubredditRepository(val database: AppDatabase) {

    val oauth = RedditAPI.oauthApi

    fun getSubreddits(refreshToken: String): DataSource.Factory<Int, SubredditEntity> {
        return database.subredditDao().subreddits(refreshToken)
    }

    fun getRemoteSubreddits(accessToken: String, scope: String, after: String): Deferred<Response<SubredditListResponse>> {
        val headers = HashMap<String, String>()
        val options = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        options["after"] = after
        return if (scope == "*")
            oauth.getDefaultSubreddits(headers, options)
        else
            oauth.getSubredditList(headers, options)
    }


    suspend fun saveSubreddits(body: SubredditListResponse, refreshToken: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val subreddits = body.data.children.map { children -> SubredditEntity(children.data.name, children.data.displayName, refreshToken, children.data.iconImg) }
            database.subredditDao().insert(subreddits)
        }
    }
}