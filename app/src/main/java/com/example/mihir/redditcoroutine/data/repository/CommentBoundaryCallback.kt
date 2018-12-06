package com.example.mihir.redditcoroutine.data.repository

import androidx.paging.PagedList
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import com.example.mihir.redditcoroutine.data.remote.response.PostDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CommentBoundaryCallback(val tokenRepository: TokenRepository,
                              val postDetailRepository: PostDetailRepository,
                              val subreddit: String,
                              val postId: String,
                              val coroutineScope: CoroutineScope) : PagedList.BoundaryCallback<CommentEntity>() {

    val flattenedList = ArrayList<PostDetailResponse.Data.Children>()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchAndStore(subreddit, postId)
    }

    private fun fetchAndStore(subreddit: String, postId: String) {
        coroutineScope.launch {
            val token = tokenRepository.getToken()
            val comments = postDetailRepository.getDetails(token.access_token, subreddit, postId)
            if (comments.isSuccessful) {
                flattenComments(comments.body()!![1].data.children)
                postDetailRepository.updateLocalPosts("t3_$postId",
                        comments.body()!![0].data.children[0].data.score,
                        comments.body()!![0].data.children[0].data.numComments)
                postDetailRepository.insertComments(mapToEntity(flattenedList, postId))
            }
        }
    }

    fun flattenComments(children: List<PostDetailResponse.Data.Children>) {
        children.forEach {
            flattenedList.add(it)
            if (it.data.replies != null)
                flattenComments(it.data.replies.data.children)
        }
    }

    fun mapToEntity(flattenedList: ArrayList<PostDetailResponse.Data.Children>, postId: String): List<CommentEntity> {
        return flattenedList.mapIndexed { index, children ->
            CommentEntity(children.data.id,
                    children.data.author,
                    children.data.body,
                    children.data.children?.joinToString(),
                    children.data.count,
                    children.data.createdUtc,
                    children.data.depth,
                    children.data.parentId,
                    index,
                    postId
            )
        }
    }

}