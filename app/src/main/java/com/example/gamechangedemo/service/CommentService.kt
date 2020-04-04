package com.example.gamechangedemo.service

import androidx.annotation.NonNull
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {
    @NonNull
    @GET("/repos/firebase/firebase-ios-sdk/issues/{id}/comments")
    fun getCommentByUserId(@Path("id") id:Long): Observable<List<Any>>
}
