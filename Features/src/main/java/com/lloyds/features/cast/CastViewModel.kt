package com.lloyds.features.cast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyod.remote.domain.repository.CastRepository
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
class CastViewModel @Inject constructor(
    private val castRepository: CastRepository
) : ViewModel(){

    private var uiState = MutableStateFlow(UiState())
    var castListState = uiState.asStateFlow()
    init {
        getCastList()
    }
    fun getCastList() {
        viewModelScope.launch {
            uiState.update {
                it.copy(isLoading = true)
            }
            castRepository.getCastList().collectLatest { response ->
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
                                caseList = response.data ?: emptyList(),
                                isCurrentShow = !castListState.value.isCurrentShow
                            )
                        }
                    }
                }
            }
        }
    }
}