package com.stylora.style.di

import com.stylora.style.data.FeedbackRepositoryImpl
import com.stylora.style.data.remote.FeedbackRemoteDataSource
import com.stylora.style.data.remote.FeedbackRemoteDataSourceImpl
import com.stylora.style.domain.repository.FeedbackRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StyloraInterfaceModule {

    @Binds
    abstract fun bindFeedbackRemoteDataSource(feedbackRemoteDataSourceImpl: FeedbackRemoteDataSourceImpl): FeedbackRemoteDataSource

    @Binds
    abstract fun bindFeedbackRepository(feedbackRepositoryImpl: FeedbackRepositoryImpl): FeedbackRepository
}