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


class HomeViewModel(val tokenRepository: TokenRepository, val postRepository: PostRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val loading: LiveData<Boolean>
        get() = _loading

    private val _loading = MutableLiveData<Boolean>()

    val error: LiveData<String?>
        get() = _error

    private val _error = MutableLiveData<String>()

    val livePagedListBuilder = LivePagedListBuilder<Int, PostEntity>(postRepository.getLocalPosts(""), 10)
            .setBoundaryCallback(PagedListSubredditPostBoundaryCallback(tokenRepository, postRepository, "", CoroutineScope(coroutineContext), _error))
            .build()

    fun refresh() {
        _loading.value = true
        launch {
            try {
                postRepository.refreshPosts(tokenRepository.getToken().access_token, "")
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
            _loading.postValue(false)
        }
    }

    fun errorShown() {
        _error.postValue(null)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
