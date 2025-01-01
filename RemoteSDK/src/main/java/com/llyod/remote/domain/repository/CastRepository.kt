package com.llyod.remote.domain.repository

import com.llyod.remote.data.model.Cast
import com.llyod.remote.utils.Response
import kotlinx.coroutines.flow.Flow

interface CastRepository {

    fun getCastList(): Flow<Response<List<Cast>>>
}