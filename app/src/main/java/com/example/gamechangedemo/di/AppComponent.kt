package com.example.gamechangedemo.di

import android.content.Context
import com.example.gamechangedemo.di.comment.CommentComponent
import com.example.gamechangedemo.helper.RxBus
import com.example.gamechangedemo.ui.IssueFragment
import com.example.gamechangedemo.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,AppSubcomponents::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(activity: IssueFragment)
    fun rxBus():RxBus
    fun commentComponent():CommentComponent.Factory
    fun homeComponent():HomePageComponent.Factory
}