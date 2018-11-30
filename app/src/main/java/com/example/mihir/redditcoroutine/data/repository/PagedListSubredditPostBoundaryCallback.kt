package com.example.mihir.redditcoroutine.data.repository

import androidx.paging.PagedList
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PagedListSubredditPostBoundaryCallback(
        private val tokenRepository: TokenRepository,
        private val postRepository: PostRepository,
        val subredditName: String,
        val coroutineScope: CoroutineScope) : PagedList.BoundaryCallback<PostEntity>() {

    var taskRunning=false

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchAndStore("")
    }

    override fun onItemAtEndLoaded(itemAtEnd: PostEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchAndStore(itemAtEnd.id)
    }

    private fun fetchAndStore(id: String) {
        if(!taskRunning)
        coroutineScope.launch {
            taskRunning=true
            val token = tokenRepository.getToken()
            val posts = postRepository.getRemotePosts(token.access_token, subredditName, id)
            if (posts.isSuccessful) {
                postRepository.savePosts(posts.body(), subredditName)
            }
            taskRunning=false
        }
    }
}