package com.example.gamechangedemo.data

import androidx.lifecycle.MutableLiveData
import com.example.gamechangedemo.ui.comment.Comment

interface CommentRepository {
    fun clear()
    fun fetchCommentFromNetwork(issue:Long)
    fun getCommentListById():MutableLiveData<List<Comment>>?
}
