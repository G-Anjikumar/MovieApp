package com.llyod.remote.utils

import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.model.Shows

data class UiState(
    val isLoading: Boolean = false,
    val showsListPage: Int = 1,
    val castListPage: Int = 1,
    val isCurrentShow: Boolean = true,
    val error: String? = null,
    val showsList: List<Shows> = emptyList(),
    val caseList: List<Cast> = emptyList()
)