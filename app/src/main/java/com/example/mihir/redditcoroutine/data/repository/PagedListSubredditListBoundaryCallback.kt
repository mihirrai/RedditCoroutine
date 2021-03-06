package com.example.mihir.redditcoroutine.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class PagedListSubredditListBoundaryCallback(val tokenRepository: TokenRepository,
                                             val subredditRepository: SubredditRepository,
                                             val coroutineScope: CoroutineScope,
                                             val _error: MutableLiveData<String>) : PagedList.BoundaryCallback<SubredditEntity>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchAndStore("")
    }

    override fun onItemAtEndLoaded(itemAtEnd: SubredditEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchAndStore(itemAtEnd.id)
    }

    private fun fetchAndStore(id: String) {
        coroutineScope.launch {
            try {
                val token = tokenRepository.getToken()
                val subreddits = subredditRepository.getRemoteSubreddits(token.access_token, token.scope, id).await()
                if (subreddits.isSuccessful) {
                    subredditRepository.saveSubreddits(subreddits.body()!!, token.refresh_token)
                }
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
        }
    }

}