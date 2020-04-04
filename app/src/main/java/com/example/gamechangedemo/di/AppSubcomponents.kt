package com.example.gamechangedemo.di

import com.example.gamechangedemo.di.comment.CommentComponent
import dagger.Module
import dagger.Subcomponent

@Module(subcomponents = [HomePageComponent::class,CommentComponent::class])
class AppSubcomponents