package com.lloyds.features.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyod.remote.domain.repository.ShowListRepository
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
    private val showListRepository: ShowListRepository
) : ViewModel() {
    private var uiState = MutableStateFlow(UiState())
    var showListState = uiState.asStateFlow()

    init {
        getShowList()
    }
    fun getShowList() {
        viewModelScope.launch {
            uiState.update {
                it.copy(isLoading = true)
            }
            uiState.update {
                it.copy(
                    isCurrentShow = !showListState.value.isCurrentShow
                )
            }
            showListRepository.getShowList().collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        uiState.update {
                            UiState(isLoading = true)
                        }
                    }

                    is Response.Error -> {
                        uiState.update {
                            UiState(isLoading = false, error = it.error)
                        }
                    }

                    is Response.Success -> {
                        uiState.update {
                            UiState(
                                isLoading = false,
                                showsList = response.data ?: emptyList(),
                                isCurrentShow = !showListState.value.isCurrentShow
                            )
                        }
                    }
                }
            }
        }
    }
}