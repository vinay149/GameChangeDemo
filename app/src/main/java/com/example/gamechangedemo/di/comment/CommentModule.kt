package com.example.gamechangedemo.di.comment

import androidx.lifecycle.ViewModelProvider
import com.example.gamechangedemo.data.CommentRepository
import com.example.gamechangedemo.data.CommentRepositoryImpl
import com.example.gamechangedemo.di.FragmentScope
import com.example.gamechangedemo.service.CommentService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class CommentModule {

    @FragmentScope
    @Provides
    fun providesCommentViewModelFactory(repositoryImpl: CommentRepositoryImpl):ViewModelProvider.Factory{
     return CommentViewModelFactory(repositoryImpl)
    }

    @FragmentScope
    @Provides
    fun providesCommentRepository(service:CommentService):CommentRepositoryImpl{
        return CommentRepositoryImpl(service)
    }

    @FragmentScope
    @Provides
    fun providesCommentService(retrofit:Retrofit):CommentService{
        return retrofit.create(CommentService::class.java)
    }
}