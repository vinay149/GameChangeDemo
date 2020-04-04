package com.example.gamechangedemo.data

import android.util.Log
import android.widget.Toast
import com.example.gamechangedemo.db.IssueDao
import com.example.gamechangedemo.model.Issue
import com.example.gamechangedemo.model.IssueListEntity
import com.example.gamechangedemo.model.IssueResponse
import com.example.gamechangedemo.service.GithubIssueService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import java.util.*
import javax.sql.DataSource
import kotlin.collections.ArrayList

class GithubIssueRepositoryImpl (val dao: IssueDao , val service:GithubIssueService): GithubIssueRepository {
    private val compositeDisposable = CompositeDisposable()
    var observable : Observable<List<Any>> ?= null
    override fun getIssueListFromNetWork() {

    }

    override fun syncIssueList() {
        observable = service.getGithubIssueList()
        observable?.let { observable->
            compositeDisposable.add(
                observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<Any>() {
                        override fun onComplete() {

                        }

                        override fun onNext(t: Any) {
                            val gson = Gson()
                            val type = object : TypeToken<Array<Issue>>() {}.type
                            val json = gson.toJson(t)
                            val item: Array<Issue> = gson.fromJson(json ,type)
                            Log.d("InsertIt:::::","insertIssueInDataBase$${item[0].body}")
                            for(i in item.indices) {
                                val issueListEntity = IssueListEntity()
                                issueListEntity.title = item.get(i).title
                                issueListEntity.body = item.get(i).body
                                issueListEntity.number = item.get(i).number
                                insertIt(issueListEntity)
                            }
                        }

                        override fun onError(e: Throwable) {
                            Log.d("Error", "Some Error occurred${e}")
                        }

                    })
            )
        }
    }

    override fun fetchIssue():androidx.paging.DataSource.Factory<Int,IssueListEntity> {
        return dao.getAllIssue()
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun deleteOldData() {
        compositeDisposable.add(Completable
            .complete()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableCompletableObserver(){
                override fun onComplete() {
                    dao.deleteAll()
                }

                override fun onError(e: Throwable) {
                }

            }))
    }

    fun insertIt(issueListEntity: IssueListEntity){
        compositeDisposable.add(Completable
            .complete()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribeWith(object :DisposableCompletableObserver(){
                override fun onComplete() {
                    dao.insert(issueListEntity)
                }

                override fun onError(e: Throwable) {
                }

            }))
    }
}

