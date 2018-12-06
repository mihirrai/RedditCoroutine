package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.response.PostDetailResponse
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

    val list: MutableList<PostDetailResponse.Data.Children> = arrayListOf()
    val res = MutableLiveData<List<PostDetailResponse>>()
    val comms = MutableLiveData<List<PostDetailResponse.Data.Children>>()

    val localPost = MutableLiveData<PostEntity>()

    fun localPost(id: String)=postDetailRepository.getLocalPost("t3_$id")

    fun list(subreddit: String,postId:String)=LivePagedListBuilder<Int,CommentEntity>(postDetailRepository.getLocalComments(postId),200)
            .setBoundaryCallback(CommentBoundaryCallback(tokenRepository,postDetailRepository,subreddit,postId, CoroutineScope(coroutineContext)))
            .build()

    fun test(subreddit: String, article: String) {
        launch {
            val t = postDetailRepository.getDetails(tokenRepository.getToken().access_token, subreddit, article)
            if (t.isSuccessful) {
                dump(t.body()!![1].data.children)
            }
            res.postValue(t.body()!!)
            comms.postValue(list)
            loading.postValue(false)
        }
    }

    fun loadMoreComments(postId: String, children: CommentEntity) {
        launch {
            val t=postDetailRepository.getMoreComments(tokenRepository.getToken().access_token,postId,children.children!!)
            if (t.isSuccessful){
                postDetailRepository.insertMoreComments(t.body()!!.json.data.things,children.position,postId)
            }
        }
    }
    fun updateLocalPost(id: String) {
        launch {
            postDetailRepository.updateLocalPosts(id,
                    res.value!![0].data.children[0].data.score,
                    res.value!![0].data.children[0].data.numComments)
        }
    }



    fun dump(children: List<PostDetailResponse.Data.Children>) {
        children.forEach {
            list.add(it)
            if (it.data.replies != null)
                dump(it.data.replies.data.children)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
