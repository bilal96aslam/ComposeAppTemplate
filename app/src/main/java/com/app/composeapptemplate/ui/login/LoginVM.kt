package com.app.composeapptemplate.ui.login

import androidx.lifecycle.ViewModel
import com.app.composeapptemplate.repository.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.app.composeapptemplate.network.apiclient.base.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val app: AppRepo) : ViewModel() {

    fun login() {
        viewModelScope.launch {
            val response = app.login("", "")
            withContext(Dispatchers.IO) {
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.token?.let {

                        }
                    }

                    is ApiResponse.Error -> {

                    }
                }
            }
        }
    }
}