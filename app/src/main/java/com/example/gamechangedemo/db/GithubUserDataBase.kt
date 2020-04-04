package com.example.gamechangedemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamechangedemo.model.IssueListEntity

@Database(entities = [IssueListEntity::class] , version = 3 , exportSchema = false)
abstract class GithubUserDataBase : RoomDatabase(){
  abstract fun userListDao():IssueDao
}

