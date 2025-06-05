package com.stylora.style.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stylora.style.data.FeedbackRepositoryImpl
import com.stylora.style.data.remote.FeedbackRemoteDataSource
import com.stylora.style.data.remote.FeedbackRemoteDataSourceImpl
import com.stylora.style.data.service.FeedbackApi
import com.stylora.style.domain.repository.FeedbackRepository
import com.stylora.style.domain.usecase.GetFeedbacksUseCase
import com.stylora.style.domain.usecase.GiveFeedbackUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StyloraModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl("https://stylora.nimusai.com/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): FeedbackApi {
        return retrofit.create(FeedbackApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedbackRemoteDataSource(
        @ApplicationContext context: Context,
        feedbackApi: FeedbackApi
    ): FeedbackRemoteDataSourceImpl {
        return FeedbackRemoteDataSourceImpl(context, feedbackApi)
    }

    @Provides
    @Singleton
    fun provideFeedbackRepository(remoteDataSource: FeedbackRemoteDataSource): FeedbackRepositoryImpl {
        return FeedbackRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGiveFeedbackUseCase(repository: FeedbackRepository): GiveFeedbackUseCase {
        return GiveFeedbackUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFeedbacksUseCase(repository: FeedbackRepository): GetFeedbacksUseCase {
        return GetFeedbacksUseCase(repository)
    }
}