package com.llyod.remote.data.repository

import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.domain.repository.CastRepository
import com.llyod.remote.domain.repository.ShowListRepository
import com.llyod.remote.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CastRepositoryImplemented @Inject constructor(
    private val fetchData: FetchData
) : CastRepository {
    override fun getCastList(): Flow<Response<List<Cast>>> = callbackFlow {
        trySend(Response.Loading())
        try {
            val showsList: List<Cast> = fetchData.getCastList()
            if (showsList.isNotEmpty()) {
                trySend(Response.Success(showsList))
            } else {
                trySend(Response.Error("Something Went Wrong"))
            }
            awaitClose { close() }
        } catch (e: Exception) {
            trySend(Response.Error(e.localizedMessage ?: "Check Your Internet Connection"))
        }
    }
}