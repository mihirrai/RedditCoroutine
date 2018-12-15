package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import com.example.mihir.redditcoroutine.data.repository.CommentBoundaryCallback
import com.example.mihir.redditcoroutine.data.repository.PostDetailRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostViewModel(private val tokenRepository: TokenRepository, private val postDetailRepository: PostDetailRepository) : ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun localPost(id: String)=postDetailRepository.getLocalPost("t3_$id")

    fun list(subreddit: String,postId:String)=LivePagedListBuilder<Int,CommentEntity>(postDetailRepository.getLocalComments(postId),200)
            .setBoundaryCallback(CommentBoundaryCallback(tokenRepository,postDetailRepository,subreddit,postId, CoroutineScope(coroutineContext)))
            .build()

    fun loadMoreComments(postId: String, children: CommentEntity) {
        launch {
            val t=postDetailRepository.getMoreComments(tokenRepository.getToken().access_token,postId,children.children!!)
            if (t.isSuccessful){
                postDetailRepository.insertMoreComments(t.body()!!.json.data.things,children.position,postId)
            }
        }
    }

    fun refresh() {
        loading.value = true
        launch {

            loading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
