package com.example.mihir.redditcoroutine.ui.viewmodel

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import com.example.mihir.redditcoroutine.data.repository.PagedListSubredditListBoundaryCallback
import com.example.mihir.redditcoroutine.data.repository.SubredditRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SubredditListViewModel(val tokenRepository: TokenRepository, val subredditRepository: SubredditRepository) : ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val token = MutableLiveData<String>().apply {
        launch { postValue(tokenRepository.getToken().refresh_token) }
    }


    fun getToken() {
        launch {
            token.postValue(tokenRepository.getToken().refresh_token)
        }
    }

    fun provide(refreshToken: String): LiveData<PagedList<SubredditEntity>> {
        d("done","done")
        return LivePagedListBuilder<Int, SubredditEntity>(subredditRepository.getSubreddits(refreshToken), 10)
                .setBoundaryCallback(PagedListSubredditListBoundaryCallback(tokenRepository, subredditRepository, CoroutineScope(coroutineContext)))
                .build()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
