package com.example.gamechangedemo.di

import com.example.gamechangedemo.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HomeModule::class])
interface HomePageComponent {
    fun inject(activity: MainActivity)
    @Subcomponent.Factory
    interface Factory {
        fun create(): HomePageComponent
    }
}