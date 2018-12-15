package com.example.mihir.redditcoroutine.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.response.MoreCommentResponse
import com.example.mihir.redditcoroutine.data.remote.response.PostDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class PostDetailRepository(val database: AppDatabase) {

    val oauth = RedditAPI.oauthApi

    suspend fun getDetails(accessToken: String, subreddit: String, article: String): Response<List<PostDetailResponse>> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        return oauth.getPostDetails(headers, subreddit, article).await()
    }

    suspend fun getMoreComments(accessToken: String, postId: String, children: String): Response<MoreCommentResponse> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $accessToken"
        val query = HashMap<String, String>()
        query["link_id"] = "t3_$postId"
        query["children"] = children
        query["api_type"] = "json"
        return oauth.getMoreComments(headers, query).await()
    }

    fun getLocalPost(id: String): LiveData<PostEntity> {
        return database.postDao().getPostById(id)
    }

    suspend fun refreshComments() = coroutineScope {
        withContext(Dispatchers.IO) {
            val comments = getRemoteComments()
        }
    }

    private fun getRemoteComments() {

    }

    suspend fun updateLocalPosts(id: String, score: Int, numComments: Int) {
        coroutineScope {
            withContext(Dispatchers.IO) {
                database.postDao().updateEntity(id, score, numComments)
            }
        }
    }

    fun getLocalComments(postId: String): DataSource.Factory<Int, CommentEntity> {
        return database.commentDao().posts(postId)
    }

    suspend fun insertComments(comments: List<CommentEntity>) {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                database.commentDao().insert(comments)
            }
        }
    }

    suspend fun insertMoreComments(things: List<MoreCommentResponse.TJson.Data.Thing>, position: Int, postId: String) {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                database.commentDao().deleteByPosition(position, postId)
                database.commentDao().updatePosition(position, things.size, postId)
                database.commentDao().insert(mapToEnity(things, position, postId))
            }
        }
    }

    fun mapToEnity(things: List<MoreCommentResponse.TJson.Data.Thing>, position: Int, postId: String): List<CommentEntity> {
        return things.mapIndexed { index, it ->
            CommentEntity(
                    it.data.id,
                    it.data.author,
                    it.data.bodyHtml,
                    "",
                    0,
                    it.data.createdUtc,
                    it.data.depth,
                    it.data.parentId,
                    position + index,
                    postId,
                    it.data.score
            )
        }
    }
}