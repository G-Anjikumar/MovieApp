package com.llyod.sample.data.domain.repository

import com.llyod.sample.data.remote.model.Cast
import com.llyod.sample.data.remote.model.Shows
import com.llyod.sample.utils.Response
import kotlinx.coroutines.flow.Flow

interface ShowListRepository {

    fun getShowList(): Flow<Response<List<Shows>>>

    fun getCastList(): Flow<Response<List<Cast>>>

    fun getShow(id: Int): Flow<Response<Shows>>
}