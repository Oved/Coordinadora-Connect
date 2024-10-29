package com.ovedev.coordinadoraconnect.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

}