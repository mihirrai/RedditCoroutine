package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.LiveData
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

class SubredditViewModel(val tokenRepository: TokenRepository, val postRepository: PostRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = MutableLiveData<Boolean>()
    val error: LiveData<String>
        get() = _error
    private val _error = MutableLiveData<String>()

    fun provide(subredditName: String) = LivePagedListBuilder<Int, PostEntity>(postRepository.getLocalPosts(subredditName), 10)
            .setBoundaryCallback(PagedListSubredditPostBoundaryCallback(tokenRepository, postRepository, subredditName, CoroutineScope(coroutineContext), _error))
            .build()

    fun refresh(subredditName: String) {
        _loading.value = true
        launch {
            try {
                postRepository.refreshPosts(tokenRepository.getToken().access_token, subredditName)
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
            _loading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
