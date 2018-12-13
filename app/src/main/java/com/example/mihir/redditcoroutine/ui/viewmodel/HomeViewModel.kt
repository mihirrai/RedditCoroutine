package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.repository.PagedListSubredditPostBoundaryCallback
import com.example.mihir.redditcoroutine.data.repository.PostRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class HomeViewModel(val tokenRepository: TokenRepository, val postRepository: PostRepository) : ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    val livePagedListBuilder = LivePagedListBuilder<Int, PostEntity>(postRepository.getLocalPosts(""), 10)
            .setBoundaryCallback(PagedListSubredditPostBoundaryCallback(tokenRepository, postRepository, "", CoroutineScope(coroutineContext)))
            .build()

    fun refresh() {
        loading.value = true
        launch {
            postRepository.refreshPosts(tokenRepository.getToken().access_token, "")
            loading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
