package com.app.composeapptemplate.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.app.composeapptemplate.repository.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.app.composeapptemplate.network.apiclient.base.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val app: AppRepo) : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState.Initial)
    val loginResponse: StateFlow<LoginScreenUiState> = _loginResponse.asStateFlow()

    var email = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set

    fun onEmailChange(userEmail: String){
        email.value = userEmail
    }

    fun onPasswordChange(userPassword: String){
        password.value = userPassword
    }

    fun login() {
        viewModelScope.launch {
            _loginResponse.value = LoginScreenUiState.Loading
            val response = app.login(email.value, password.value)
            withContext(Dispatchers.IO) {
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.token?.let {
                            _loginResponse.value =
                                LoginScreenUiState.Success(obj = response.data)
                        }
                    }

                    is ApiResponse.Error -> {
                        _loginResponse.value =
                            LoginScreenUiState.Error(response.error.message)
                    }
                }
            }
        }
    }
}