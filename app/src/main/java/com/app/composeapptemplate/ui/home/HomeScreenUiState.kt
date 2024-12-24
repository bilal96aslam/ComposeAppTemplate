package com.app.composeapptemplate.ui.home

import com.app.composeapptemplate.network.response.UserData

sealed interface HomeScreenUiState {
    data object Initial : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data class Success(val obj: UserData) : HomeScreenUiState
    data class Error(val msg: String) : HomeScreenUiState
}
