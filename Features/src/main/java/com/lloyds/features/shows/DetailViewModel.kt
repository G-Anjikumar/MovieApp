package com.lloyds.features.shows

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyod.remote.domain.repository.ShowRepository
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val showRepository: ShowRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val showId = savedStateHandle.get<Int>("showId")

    private var detailState = MutableStateFlow(DetailsState())
    val detailsState = detailState.asStateFlow()

    init {
        getShow(showId ?: -1)
    }

    private fun getShow(showId: Int) {
        viewModelScope.launch {
            showRepository.getShow(showId).collectLatest { response ->
                val updateUiState = when (response) {
                    is Response.Loading -> DetailsState(isLoading = true)
                    is Response.Success -> DetailsState(
                        isLoading = false,
                        shows = response.data
                    )

                    is Response.Error -> DetailsState(isLoading = false, error = AppConstants.API_ERROR_MESSAGE)
                }
                detailState.update { updateUiState }
            }
        }
    }
}