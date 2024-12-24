package com.app.composeapptemplate.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composeapptemplate.network.apiclient.base.ApiResponse
import com.app.composeapptemplate.repository.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val app: AppRepo) : ViewModel() {

    private val _homeScreenResponse = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Initial)
    val homeScreenResponse: StateFlow<HomeScreenUiState> = _homeScreenResponse.asStateFlow()

    fun getUserData() {
        viewModelScope.launch {
            _homeScreenResponse.value = HomeScreenUiState.Loading
            val response = app.getUser("2")
            withContext(Dispatchers.IO) {
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.data.let { data ->
                            data?.let {
                                _homeScreenResponse.value = HomeScreenUiState.Success(obj = data)
                            }
                        }
                    }

                    is ApiResponse.Error -> {
                        _homeScreenResponse.value =
                            HomeScreenUiState.Error(response.error.message)
                    }
                }
            }
        }
    }
}