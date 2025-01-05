package com.lloyds.features.shows

import com.llyod.remote.data.model.Shows


data class DetailsState(
    val isLoading: Boolean = false,
    val shows: Shows? = null,
    val error: String? = null
)
