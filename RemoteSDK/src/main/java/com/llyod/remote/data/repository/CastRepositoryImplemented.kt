package com.llyod.remote.data.repository

import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.domain.repository.CastRepository
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class CastRepositoryImplemented @Inject constructor(
    private val fetchData: FetchData
) : CastRepository {
    override fun getCastList(): Flow<Response<List<Cast>>> = flow {
        emit(Response.Loading())
        try {
            val showsList: List<Cast> = fetchData.getCastList()
            if (showsList.isNotEmpty()) emit(Response.Success(showsList)) else emit(
                Response.Error(
                    AppConstants.API_ERROR_MESSAGE
                )
            )
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: AppConstants.NETWORK_ERROR_MESSAGE))
        }
    }
}