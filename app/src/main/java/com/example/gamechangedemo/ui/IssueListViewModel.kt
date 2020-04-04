package com.example.gamechangedemo.ui

import android.graphics.pdf.PdfDocument
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gamechangedemo.data.GithubIssueRepository
import com.example.gamechangedemo.db.IssueDao
import com.example.gamechangedemo.model.IssueListEntity
import com.example.gamechangedemo.service.GithubIssueService
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class IssueListViewModel (
    val repository: GithubIssueRepository
) : ViewModel() {

    @Inject
    lateinit var issueService: GithubIssueService
    private val pageSize = 30
    var issueId:Long?=null
    private val compositeDisposable = CompositeDisposable()
    var issueList: LiveData<PagedList<IssueListEntity>>? = null

    init {
        val pagingConfig = PagedList.Config.Builder()
            .setPageSize(30)
            .setInitialLoadSizeHint(pageSize * 3)
            .setEnablePlaceholders(true)
            .build()
        issueList = LivePagedListBuilder<Int, IssueListEntity>(
            repository.fetchIssue(),
            pagingConfig
        ).build()
    }

    fun getLatestIssueList(): LiveData<PagedList<IssueListEntity>>? {
        return issueList
    }

    fun syncData() {
        repository.syncIssueList()
    }

    fun deleteAllOldData() {
        repository.deleteOldData()
    }

    fun updateIssueId(issueId:Long) {
        issueId.let {
            this.issueId = it
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}