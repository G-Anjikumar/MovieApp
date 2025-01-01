package com.llyod.remote.data.remote

import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.model.Shows
import com.llyod.remote.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Path

interface FetchData {

    @GET(AppConstants.END_POINT)
    suspend fun getShowList(): List<Shows>

    @GET(AppConstants.CAST_POINT)
    suspend fun getCastList(): List<Cast>

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): Shows
}
