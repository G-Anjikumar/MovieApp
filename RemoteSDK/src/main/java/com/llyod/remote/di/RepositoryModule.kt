package com.llyod.remote.di

import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.data.repository.CastRepositoryImplemented
import com.llyod.remote.data.repository.ShowListRepositoryImpl
import com.llyod.remote.domain.repository.CastRepository
import com.llyod.remote.domain.repository.ShowListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideShowRepo(fetchData: FetchData): ShowListRepository =
        ShowListRepositoryImpl(fetchData)


    @Provides
    @Singleton
    fun provideCastRepo(fetchData: FetchData): CastRepository =
        CastRepositoryImplemented(fetchData)
}