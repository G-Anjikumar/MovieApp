package com.llyod.sample.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyod.sample.data.domain.repository.ShowListRepository
import com.llyod.sample.ui.home.viewmodel.DetailsState
import com.llyod.sample.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val showListRepository: ShowListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val showId = savedStateHandle.get<Int>("showId")

    private var _detialsState = MutableStateFlow(DetailsState())
    val detailsState = _detialsState.asStateFlow()

    init {
        getMovie(showId ?: -1)
    }

    private fun getMovie(showId: Int) {
        viewModelScope.launch {
            _detialsState.update {
                it.copy(isLoading = true)
            }
            showListRepository.getShow(showId).collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _detialsState.update {
                            DetailsState(isLoading = true)
                        }
                    }

                    is Response.Error -> {
                        _detialsState.update {
                            DetailsState(isLoading = false)
                        }
                    }

                    is Response.Success -> {
                        _detialsState.update {
                            DetailsState(
                                isLoading = false,
                                shows = response.data
                            )
                        }
                    }
                }
            }
        }
    }
}