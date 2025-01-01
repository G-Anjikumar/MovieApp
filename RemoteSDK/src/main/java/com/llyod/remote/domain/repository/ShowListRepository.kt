package com.llyod.remote.domain.repository

import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.model.Shows
import com.llyod.remote.utils.Response
import kotlinx.coroutines.flow.Flow

interface ShowListRepository {

    fun getShowList(): Flow<Response<List<Shows>>>

    fun getShow(id: Int): Flow<Response<Shows>>
}