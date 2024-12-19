package com.llyod.sample.di

import com.llyod.sample.data.domain.repository.ShowListRepository
import com.llyod.sample.data.remote.repositoryimpl.ShowListRepositoryImpl
import com.llyod.sample.network.FetchData
import com.llyod.sample.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): FetchData =
        Builder()
            .baseUrl(AppConstants.API_BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchData::class.java)

    @Provides
    @Singleton
    fun provideShowRepo(fetchData: FetchData): ShowListRepository =
        ShowListRepositoryImpl(fetchData)

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

}