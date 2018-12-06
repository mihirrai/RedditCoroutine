package com.example.mihir.redditcoroutine.data.repository

import androidx.paging.DataSource
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.response.SubredditResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class PostRepository(val database: AppDatabase) {
    val oauth = RedditAPI.oauthApi

    suspend fun getRemotePosts(accessToken: String, subredditName: String, after: String): Response<SubredditResponse> {
        return if (subredditName.isEmpty())
            getRemoteFrontpagePosts(accessToken, after)
        else getRemoteSubredditPosts(accessToken, subredditName, after)
    }

    suspend fun getRemoteFrontpagePosts(accessToken: String, after: String): Response<SubredditResponse> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        return oauth.getFrontpage(headers, after).await()
    }

    suspend fun getRemoteSubredditPosts(accessToken: String, subredditName: String, after: String): Response<SubredditResponse> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        val query = HashMap<String, String>()
        query["after"] = after
        return oauth.getSubreddit(headers, subredditName, query).await()
    }

    suspend fun savePosts(body: SubredditResponse?, subredditName: String) = coroutineScope {
        async(Dispatchers.IO) {
            val posts = mapToEntity(body!!.data.children, subredditName)
            database.postDao().insertList(posts)
        }.await()
    }

    suspend fun refreshPosts(accessToken: String, subredditName: String) = coroutineScope {
        async(Dispatchers.IO) {
            val posts = getRemotePosts(accessToken, subredditName, "")
            if (posts.isSuccessful)
                database.runInTransaction {
                    database.postDao().deleteBySubreddit(subredditName)
                    database.postDao().insertList(mapToEntity(posts.body()!!.data.children, subredditName))
                }
        }.await()
    }

    fun mapToEntity(children: List<SubredditResponse.Data.Children>, subredditName: String): List<PostEntity> {
        return children.map { children ->

            val previewUrl = if (children.data.preview != null) {
                if (children.data.preview.images.get(0).resolutions.isNotEmpty())
                    children.data.preview.images.get(0).resolutions.last().url
                else ""
            } else
                null
            PostEntity(children.data.name,
                    children.data.title,
                    children.data.author,
                    children.data.score,
                    children.data.subreddit,
                    children.data.numComments,
                    children.data.createdUtc,
                    children.data.over18,
                    children.data.linkFlairText,
                    children.data.gilded,
                    subredditName,
                    previewUrl,
                    children.data.url,
                    children.data.selftext,
                    children.data.isSelf)
        }

    }

    fun getLocalPosts(subredditName: String): DataSource.Factory<Int, PostEntity> {
        return database.postDao().posts(subredditName)
    }

    suspend fun deleteBySubreddit(subredditName: String) = coroutineScope {
        async(Dispatchers.IO) {
            database.postDao().deleteBySubreddit(subredditName)
        }.await()
    }
}