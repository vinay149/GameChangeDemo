package com.example.gamechangedemo.service

import androidx.annotation.NonNull
import com.example.gamechangedemo.model.Issue
import com.example.gamechangedemo.model.IssueResponse
import io.reactivex.Observable
import org.jetbrains.annotations.NotNull
import retrofit2.http.GET

interface GithubIssueService {
    @NonNull
    @GET("/repos/firebase/firebase-ios-sdk/issues")
    fun getGithubIssueList(): Observable<List<Any>>
}

