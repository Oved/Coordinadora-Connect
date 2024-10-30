package com.ovedev.coordinadoraconnect.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.usecase.LoginUseCase
import com.ovedev.coordinadoraconnect.domain.usecase.VerifyIsSessionActive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val verifyIsSessionActive: VerifyIsSessionActive,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _responseLogin = MutableLiveData<Response<LoginResponse>>()
    val responseLogin: LiveData<Response<LoginResponse>> get() = _responseLogin

    private val _isSessionActive = MutableLiveData<Response<Boolean>>()
    val isSessionActive: LiveData<Response<Boolean>> get() = _isSessionActive

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginUseCase(username, password).collect { response ->
                _responseLogin.value = response
            }
        }
    }

    fun isSessionActive() {
        viewModelScope.launch {
            verifyIsSessionActive().collect { response ->
                _isSessionActive.value = response
            }
        }
    }

}