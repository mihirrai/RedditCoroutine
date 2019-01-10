package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mihir.redditcoroutine.data.local.entity.TokenEntity
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(val tokenRepository: TokenRepository) : ViewModel(), CoroutineScope {

    val liveTokenEntity = tokenRepository.getTokenLive()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val tokenEntity: LiveData<TokenEntity>
        get() = _tokenEntity
    private val _tokenEntity = MutableLiveData<TokenEntity>()

    val error: LiveData<String?>
        get() = _error
    private val _error = MutableLiveData<String>()

    init {
        launch {
            try {
                _tokenEntity.postValue(tokenRepository.getToken())
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
        }
    }

    fun errorShown() {
        _error.postValue(null)
    }

    fun updateToken() {
        launch {
            _tokenEntity.postValue(tokenRepository.getToken())
        }
    }
}
