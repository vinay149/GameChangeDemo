package com.example.gamechangedemo.model

data class IssueResponse(val listOfIssue:List<Issue>)
data class Issue (
    val title : String,
    val number:Long,
    val body : String
)