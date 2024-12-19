package com.llyod.sample.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyod.sample.data.domain.repository.ShowListRepository
import com.llyod.sample.data.remote.model.Cast
import com.llyod.sample.data.remote.model.Shows
import com.llyod.sample.ui.home.presentation.ShowListUiState
import com.llyod.sample.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowAndCastViewModel @Inject constructor(
    private val showListRepository: ShowListRepository
) : ViewModel() {
    private var _showListState = MutableStateFlow(ShowListState())
    var showListState = _showListState.asStateFlow()
    var searchState = MutableStateFlow(String)

    init {
        getShowList()
    }

    fun onEvent(event: ShowListUiState) {
        when (event) {
            ShowListUiState.Navigate -> {
                getShowList()
                _showListState.update {
                    it.copy(
                        isCurrentShow = !showListState.value.isCurrentShow
                    )
                }
            }

            is ShowListUiState.Paginate -> {
                getCastList()
            }
        }
    }

    private fun getShowList() {
        viewModelScope.launch {
            _showListState.update {
                it.copy(isLoading = true)
            }
            showListRepository.getShowList().collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _showListState.update {
                            ShowListState(isLoading = true)
                        }
                    }

                    is Response.Error -> {
                        _showListState.update {
                            ShowListState(isLoading = false, error = it.error)
                        }
                    }

                    is Response.Success -> {
                        _showListState.update {
                            ShowListState(
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

    private fun getCastList() {
        viewModelScope.launch {
            _showListState.update {
                it.copy(isLoading = true)
            }
            showListRepository.getCastList().collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _showListState.update {
                            ShowListState(isLoading = true)
                        }
                    }

                    is Response.Error -> {
                        _showListState.update {
                            ShowListState(isLoading = false, error = it.error)
                        }
                    }

                    is Response.Success -> {
                        _showListState.update {
                            ShowListState(
                                isLoading = false,
                                caseList = response.data ?: emptyList(),
                                isCurrentShow = !showListState.value.isCurrentShow
                            )
                        }
                    }
                }
            }
        }
    }

    fun searchEvent(search: String) {

    }


}

data class ShowListState(
    val isLoading: Boolean = false,
    val showsListPage: Int = 1,
    val castListPage: Int = 1,
    val isCurrentShow: Boolean = true,
    val error: String? = null,
    val showsList: List<Shows> = emptyList(),
    val caseList: List<Cast> = emptyList()
)