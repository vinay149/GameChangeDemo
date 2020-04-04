package com.example.gamechangedemo.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.gamechangedemo.data.GithubIssueRepository
import com.example.gamechangedemo.data.GithubIssueRepositoryImpl
import com.example.gamechangedemo.db.GithubUserDataBase
import com.example.gamechangedemo.db.IssueDao
import com.example.gamechangedemo.helper.RxBus
import com.example.gamechangedemo.helper.RxBusImpl
import com.example.gamechangedemo.service.GithubIssueService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.inject.Singleton


@Module(includes = [HomeModule::class])
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.github.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = 10;
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

            .dispatcher(dispatcher)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesEventBus():RxBus{
        return RxBusImpl()
    }


    @Singleton
    @Provides
    fun provideViewModelFactory( repository: GithubIssueRepository): ViewModelProvider.Factory {
        return  ViewModelFactory(repository)
    }

    @Singleton
    @Provides
    fun provideGithubIssueService(retrofit:Retrofit):GithubIssueService{
        return retrofit.create(GithubIssueService::class.java)
    }


    @Singleton
    @Provides
    fun providesRepository(dao: IssueDao, service:GithubIssueService):GithubIssueRepository{
        return GithubIssueRepositoryImpl(dao, service)
    }

    @Singleton
    @Provides
    fun providesDao(dataBase: GithubUserDataBase):IssueDao{
        return dataBase.userListDao()
    }


    @Singleton
    @Provides
    fun providesPdfDataBase(context: Context): GithubUserDataBase {
        return Room.databaseBuilder(
            context, GithubUserDataBase::class.java, "github_user_list_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

}