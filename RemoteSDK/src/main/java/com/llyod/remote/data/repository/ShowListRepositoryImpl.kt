package com.llyod.remote.data.repository

import com.llyod.remote.data.model.Shows
import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.domain.repository.ShowListRepository
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowListRepositoryImpl @Inject constructor(
    private val fetchData: FetchData
) : ShowListRepository {
    override fun getShowList(): Flow<Response<List<Shows>>> = flow {
        emit(Response.Loading())
        try {
            val showsList = fetchData.getShowList()
            if (showsList.isNotEmpty()) emit(Response.Success(showsList)) else emit(
                Response.Error(
                    AppConstants.API_ERROR_MESSAGE
                )
            )
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: AppConstants.NETWORK_ERROR_MESSAGE))
        }
    }


    override fun getShow(id: Int): Flow<Response<Shows>> = flow {
        emit(Response.Loading())
        try {
            val showsList: Shows = fetchData.getShow(id)
            emit(Response.Success(showsList))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: AppConstants.NETWORK_ERROR_MESSAGE))
        }
    }
}
