package com.ovedev.coordinadoraconnect.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _responseLogin = MutableLiveData<Response<LoginResponse>>()
    val responseLogin: LiveData<Response<LoginResponse>> get() = _responseLogin

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginUseCase(username, password).collect { response ->
                _responseLogin.value = response
            }
        }
    }

}