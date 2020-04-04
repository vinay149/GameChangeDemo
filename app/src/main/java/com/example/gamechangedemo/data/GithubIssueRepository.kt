package com.example.gamechangedemo.data

import com.example.gamechangedemo.model.IssueListEntity

interface GithubIssueRepository {
    fun getIssueListFromNetWork()
    fun clear()
    fun syncIssueList()
    fun fetchIssue():androidx.paging.DataSource.Factory<Int, IssueListEntity>
    fun deleteOldData()
}