package com.example.gamechangedemo.ui.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamechangedemo.data.CommentRepositoryImpl
import io.reactivex.disposables.CompositeDisposable

class CommentViewModel(val commentRepositoryImpl: CommentRepositoryImpl) : ViewModel(){
    val compositeDisposable = CompositeDisposable()
    private var commentList:MutableLiveData<List<Comment>> ?= null

    fun fetchComment(issueId:Long){
        commentRepositoryImpl.fetchCommentFromNetwork(issueId)
        commentList = commentRepositoryImpl.getCommentListById()
    }

    fun getAllCommentListById():MutableLiveData<List<Comment>>?{
        return commentList
    }
    override fun onCleared() {
        super.onCleared()
    }
}