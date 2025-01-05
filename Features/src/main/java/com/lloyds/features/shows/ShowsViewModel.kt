package com.lloyds.features.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyod.remote.domain.repository.ShowRepository
import com.llyod.remote.utils.Response
import com.llyod.remote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {
    private var uiState = MutableStateFlow(UiState())
    var showListState = uiState.asStateFlow()

    init {
        getShowList()
    }

    fun getShowList() {
        viewModelScope.launch {
            showRepository.getShowList().collectLatest { response ->
                val updateUiState = when (response) {
                    is Response.Loading -> UiState(isLoading = true)
                    is Response.Success -> UiState(
                        isLoading = false,
                        showsList = response.data ?: emptyList(),
                        isCurrentShow = !showListState.value.isCurrentShow
                    )

                    is Response.Error -> UiState(isLoading = false, error = response.message)
                }
                uiState.update { updateUiState }
            }
        }
    }
}