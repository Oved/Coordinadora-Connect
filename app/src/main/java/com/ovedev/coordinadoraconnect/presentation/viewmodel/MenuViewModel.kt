package com.ovedev.coordinadoraconnect.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.domain.usecase.CloseSessionUseCase
import com.ovedev.coordinadoraconnect.domain.usecase.GetPdfLocationUseCase
import com.ovedev.coordinadoraconnect.domain.usecase.VerifyContinueInSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val verifyContinueInSessionUseCase: VerifyContinueInSessionUseCase,
    private val closeSessionUseCase: CloseSessionUseCase,
    private val getPdfLocationUseCase: GetPdfLocationUseCase
) : ViewModel() {

    private val _responsePdf = MutableLiveData<Response<PdfResponse>>()
    val responsePdf: LiveData<Response<PdfResponse>> get() = _responsePdf

    private val _responseContinueInSession = MutableLiveData<Response<Boolean>>()
    val responseContinueInSession: LiveData<Response<Boolean>> get() = _responseContinueInSession

    private val _responseCloseSession = MutableLiveData<Response<Boolean>>()
    val responseCloseSession: LiveData<Response<Boolean>> get() = _responseCloseSession

    fun getPdfLocation() {
        viewModelScope.launch {
            getPdfLocationUseCase().collect { response ->
                _responsePdf.value = response
            }
        }
    }

    fun verifyContinueInSession() {
        viewModelScope.launch {
            verifyContinueInSessionUseCase().collect { response ->
                _responseContinueInSession.value = response
            }
        }
    }

    fun closeSession() {
        viewModelScope.launch {
            closeSessionUseCase().collect { response ->
                _responseCloseSession.value = response
            }
        }
    }

}