package com.example.mihir.redditcoroutine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.repository.PostDetailRepository
import com.example.mihir.redditcoroutine.data.repository.PostRepository
import com.example.mihir.redditcoroutine.data.repository.SubredditRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import com.example.mihir.redditcoroutine.ui.viewmodel.*

class ViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(TokenRepository(database), PostRepository(database)) as T
            modelClass.isAssignableFrom(SubredditListViewModel::class.java) -> SubredditListViewModel(TokenRepository(database), SubredditRepository(database)) as T
            modelClass.isAssignableFrom(SubredditViewModel::class.java) -> SubredditViewModel(TokenRepository(database), PostRepository(database)) as T
            modelClass.isAssignableFrom(PostViewModel::class.java) -> PostViewModel(TokenRepository(database),PostDetailRepository(database)) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(TokenRepository(database)) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(TokenRepository(database)) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}