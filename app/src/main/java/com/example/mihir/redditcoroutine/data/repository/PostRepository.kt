package com.example.mihir.redditcoroutine.data.repository

import androidx.paging.DataSource
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.response.SubredditResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class PostRepository(val database: AppDatabase) {

    private val oauth = RedditAPI.oauthApi

    fun getRemotePosts(accessToken: String, subredditName: String, after: String): Deferred<Response<SubredditResponse>> {
        return if (subredditName.isEmpty())
            getRemoteFrontpagePosts(accessToken, after)
        else getRemoteSubredditPosts(accessToken, subredditName, after)
    }

    suspend fun savePosts(body: SubredditResponse?, subredditName: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val posts = mapToEntity(body!!.data.children, subredditName)
            database.postDao().insertList(posts)
        }
    }

    suspend fun refreshPosts(accessToken: String, subredditName: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val posts = getRemotePosts(accessToken, subredditName, "").await()
            if (posts.isSuccessful)
                database.runInTransaction {
                    database.postDao().deleteBySubreddit(subredditName)
                    database.postDao().insertList(mapToEntity(posts.body()!!.data.children, subredditName))
                }
        }
    }

    private fun getRemoteFrontpagePosts(accessToken: String, after: String): Deferred<Response<SubredditResponse>> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        return oauth.getFrontpage(headers, after)
    }

    private fun getRemoteSubredditPosts(accessToken: String, subredditName: String, after: String): Deferred<Response<SubredditResponse>> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        val query = HashMap<String, String>()
        query["after"] = after
        return oauth.getSubreddit(headers, subredditName, query)
    }

    private fun mapToEntity(children: List<SubredditResponse.Data.Children>, subredditName: String): List<PostEntity> {
        return children.map { children ->
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
                    children.data.preview?.images?.get(0)?.source?.url,
                    children.data.url,
                    children.data.selftext,
                    children.data.isSelf)
        }

    }

    fun getLocalPosts(subredditName: String): DataSource.Factory<Int, PostEntity> {
        return database.postDao().posts(subredditName)
    }
}