package com.llyod.remote.data.repository

import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.model.Shows
import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.domain.repository.ShowListRepository
import com.llyod.remote.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ShowListRepositoryImpl @Inject constructor(
    private val fetchData: FetchData
) : ShowListRepository {
    override fun getShowList(): Flow<Response<List<Shows>>> = callbackFlow {
        trySend(Response.Loading())
        try {
            val showsList: List<Shows> = fetchData.getShowList()
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


    override fun getShow(id: Int): Flow<Response<Shows>> = callbackFlow {
        try {
            val showsList: Shows = fetchData.getShow(id)
            trySend(Response.Success(showsList))
            awaitClose { close() }
        } catch (e: Exception) {
            trySend(Response.Error(e.localizedMessage ?: "Check Your Internet Connection"))
        }
    }
}
