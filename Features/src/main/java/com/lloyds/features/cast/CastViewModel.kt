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
) : ViewModel() {

    private var uiState = MutableStateFlow(UiState())
    var castListState = uiState.asStateFlow()

    init {
        getCastList()
    }
    fun getCastList() {
        viewModelScope.launch {
            castRepository.getCastList().collectLatest { response ->
                val updatedUiState = when (response) {
                    is Response.Loading -> UiState(isLoading = true)
                    is Response.Error -> UiState(isLoading = false, error = response.message)
                    is Response.Success -> UiState(
                        isLoading = false,
                        caseList = response.data.orEmpty(),
                        isCurrentShow = !castListState.value.isCurrentShow
                    )
                }
                uiState.update { updatedUiState }
            }
        }
    }
}