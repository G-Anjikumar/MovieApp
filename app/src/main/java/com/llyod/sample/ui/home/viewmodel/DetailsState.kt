package com.llyod.sample.ui.home.viewmodel

import com.llyod.sample.data.remote.model.Shows

data class DetailsState(
    val isLoading: Boolean = false,
    val shows: Shows? = null
)
