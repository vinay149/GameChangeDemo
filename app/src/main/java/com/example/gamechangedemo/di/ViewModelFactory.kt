package com.example.gamechangedemo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamechangedemo.data.GithubIssueRepository
import com.example.gamechangedemo.ui.IssueListViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val repository: GithubIssueRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssueListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IssueListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}