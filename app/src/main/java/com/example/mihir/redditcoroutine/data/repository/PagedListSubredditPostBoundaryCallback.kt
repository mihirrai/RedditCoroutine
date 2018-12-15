package com.example.mihir.redditcoroutine.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class PagedListSubredditPostBoundaryCallback(
        private val tokenRepository: TokenRepository,
        private val postRepository: PostRepository,
        val subredditName: String,
        val coroutineScope: CoroutineScope,
        val error: MutableLiveData<String>) : PagedList.BoundaryCallback<PostEntity>() {


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchAndStore("")
    }

    override fun onItemAtEndLoaded(itemAtEnd: PostEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchAndStore(itemAtEnd.id)
    }

    private fun fetchAndStore(id: String) {
        coroutineScope.launch {
            try {
                val token = tokenRepository.getToken()
                val posts = postRepository.getRemotePosts(token.access_token, subredditName, id).await()
                if (posts.isSuccessful) {
                    postRepository.savePosts(posts.body(), subredditName)
                }
            } catch (e: Exception) {
                error.postValue(e.localizedMessage)
            }
        }
    }
}