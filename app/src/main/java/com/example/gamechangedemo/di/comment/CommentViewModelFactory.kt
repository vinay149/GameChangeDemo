package com.example.gamechangedemo.di.comment


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamechangedemo.data.CommentRepositoryImpl
import com.example.gamechangedemo.ui.comment.CommentViewModel
import javax.inject.Inject

class CommentViewModelFactory @Inject constructor(val repositoryImpl: CommentRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(repositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}