package com.example.gamechangedemo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gamechangedemo.model.Issue
import com.example.gamechangedemo.service.CommentService
import com.example.gamechangedemo.ui.comment.Comment
import com.example.gamechangedemo.ui.comment.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class CommentRepositoryImpl(val service: CommentService) : CommentRepository {
    val compositeDisposable = CompositeDisposable()
    var commentList:MutableLiveData<List<Comment>> = MutableLiveData()
    var observable:Observable<List<Any>> ? = null
    override fun fetchCommentFromNetwork(issue:Long) {
          observable = service.getCommentByUserId(issue)
        observable?.let { observable->
            compositeDisposable.add(
                    observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<Any>>() {
                        override fun onComplete() {
                        }

                        override fun onNext(t: List<Any>) {
                            Log.d("CommentList", "Comment${t}")
                            val type = object : TypeToken<Array<Comment>>() {}.type
                            val  gson = Gson()
                            val json = gson.toJson(t)
                            val list:MutableList<Comment> = ArrayList()
                            val item: Array<Comment> = gson.fromJson(json ,type)
                            for(i in item.indices){
                                val userData = UserData(item[i].user.login)
                                val comment =  Comment(userData,item[i].body)
                                list.add(comment)
                            }
                            commentList.postValue(list)
                        }

                        override fun onError(e: Throwable) {
                            Log.d("limitExceed","${e}")
                        }

                    })
            )
        }
    }

    override fun getCommentListById(): MutableLiveData<List<Comment>>? {
        return commentList
    }

    override fun clear(){
        compositeDisposable.clear()
    }

}
