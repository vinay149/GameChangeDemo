package com.example.gamechangedemo.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_issue_list")
class IssueListEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "body")
    var body: String? = null

    @ColumnInfo
    var number:Long? = null

}
