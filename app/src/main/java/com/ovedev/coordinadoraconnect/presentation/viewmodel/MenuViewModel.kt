package com.ovedev.coordinadoraconnect.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.domain.usecase.GetPdfLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getPdfLocationUseCase: GetPdfLocationUseCase
) : ViewModel() {

    private val _responsePdf = MutableLiveData<Response<PdfResponse>>()
    val responsePdf: LiveData<Response<PdfResponse>> get() = _responsePdf

    fun getPdfLocation() {
        viewModelScope.launch {
            getPdfLocationUseCase().collect { response ->
                _responsePdf.value = response
            }
        }
    }

}