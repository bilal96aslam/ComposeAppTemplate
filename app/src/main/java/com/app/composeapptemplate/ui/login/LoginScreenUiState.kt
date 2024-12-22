package com.app.composeapptemplate.ui.login

import com.app.composeapptemplate.network.response.LoginResponse

sealed interface LoginScreenUiState {
    data object Initial : LoginScreenUiState
    data object Loading : LoginScreenUiState
    data class Success(val obj: LoginResponse) : LoginScreenUiState
    data class Error(val msg: String) : LoginScreenUiState
}
