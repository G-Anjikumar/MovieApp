package com.llyod.remote.di

import com.llyod.remote.data.repository.CastRepositoryImplemented
import com.llyod.remote.data.repository.ShowRepositoryImpl
import com.llyod.remote.domain.repository.CastRepository
import com.llyod.remote.domain.repository.ShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideShowRepo(showListRepositoryImpl: ShowRepositoryImpl): ShowRepository

    @Binds
    @Singleton
    abstract fun provideCastRepo(castRepositoryImplemented: CastRepositoryImplemented): CastRepository
}