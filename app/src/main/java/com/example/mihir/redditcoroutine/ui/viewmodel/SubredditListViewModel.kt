package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import com.example.mihir.redditcoroutine.data.local.entity.TokenEntity
import com.example.mihir.redditcoroutine.data.repository.PagedListSubredditListBoundaryCallback
import com.example.mihir.redditcoroutine.data.repository.SubredditRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SubredditListViewModel(val tokenRepository: TokenRepository, val subredditRepository: SubredditRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val error: LiveData<String>
        get() = _error
    private val _error = MutableLiveData<String>()

    val tokenEntity: LiveData<TokenEntity>
        get() = _tokenEntity
    private val _tokenEntity = MutableLiveData<TokenEntity>()

    init {
        launch {
            try {
                _tokenEntity.postValue(tokenRepository.getToken())
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
        }
    }

    fun provide(refreshToken: String): LiveData<PagedList<SubredditEntity>> {
        return LivePagedListBuilder<Int, SubredditEntity>(subredditRepository.getSubreddits(refreshToken), 10)
                .setBoundaryCallback(PagedListSubredditListBoundaryCallback(tokenRepository, subredditRepository, CoroutineScope(coroutineContext), _error))
                .build()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
