package com.example.gamechangedemo.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamechangedemo.model.IssueListEntity

@Dao
interface IssueDao {

    @Query("SELECT *FROM github_issue_list")
    fun getAllIssue():DataSource.Factory<Int, IssueListEntity>

    @Query("DELETE FROM github_issue_list")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(issue:IssueListEntity)
}