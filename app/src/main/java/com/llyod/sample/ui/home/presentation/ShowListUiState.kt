package com.llyod.sample.ui.home.presentation

sealed interface ShowListUiState {

    data object Paginate : ShowListUiState
    data object Navigate : ShowListUiState
}