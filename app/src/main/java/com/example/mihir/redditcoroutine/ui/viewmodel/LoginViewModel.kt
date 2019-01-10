package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(val tokenRepository: TokenRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val success: LiveData<Boolean>
        get() = _success
    private val _success = MutableLiveData<Boolean>()

    fun authCodeToAccessToken(authCode: String) {
        launch {
            try {
                tokenRepository.newLoginToken(authCode)
                _success.postValue(true)
            } catch (e: Exception) {
                println(e.localizedMessage)
                _success.postValue(false)
            }
        }
    }

}