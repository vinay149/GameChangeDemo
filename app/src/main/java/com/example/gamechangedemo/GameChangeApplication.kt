package com.example.gamechangedemo

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Configuration
import com.example.gamechangedemo.di.AppComponent
import com.example.gamechangedemo.di.DaggerAppComponent

class GameChangeApplication : Application(){


    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }


    override fun onCreate() {
        instance = this
        super.onCreate()
    }


    companion object {
        lateinit var instance: GameChangeApplication
    }

}