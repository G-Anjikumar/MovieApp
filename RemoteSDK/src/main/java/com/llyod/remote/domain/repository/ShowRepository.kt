package com.llyod.remote.domain.repository

import com.llyod.remote.data.model.Shows
import com.llyod.remote.utils.Response
import kotlinx.coroutines.flow.Flow

interface ShowRepository {

    fun getShowList(): Flow<Response<List<Shows>>>

    fun getShow(id: Int): Flow<Response<Shows>>
}