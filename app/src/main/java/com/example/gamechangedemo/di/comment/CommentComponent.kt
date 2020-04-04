package com.example.gamechangedemo.di.comment

import com.example.gamechangedemo.di.FragmentScope
import com.example.gamechangedemo.di.HomePageComponent
import com.example.gamechangedemo.ui.MainActivity
import com.example.gamechangedemo.ui.comment.CommentPageFragment
import dagger.Component
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CommentModule::class])
interface CommentComponent {

    fun inject(activity: CommentPageFragment)
    @Subcomponent.Factory
    interface Factory {
        fun create(): CommentComponent
    }
}
