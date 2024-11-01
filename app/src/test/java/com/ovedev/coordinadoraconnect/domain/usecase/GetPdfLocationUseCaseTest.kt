package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.domain.repository.MenuRepository
import com.ovedev.coordinadoraconnect.presentation.model.Position
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetPdfLocationUseCaseTest {

    @Mock
    private lateinit var menuRepository: MenuRepository

    private lateinit var getPdfLocationUseCase: GetPdfLocationUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getPdfLocationUseCase = GetPdfLocationUseCase(menuRepository)
    }

    @Test
    fun `when invoke should return pdf response successfully`() = runTest {
        // Given
        val pdfResponse = PdfResponse(
            isError = false,
            base64Data = "test",
            positions = listOf(Position(1.0, 2.0))
        )
        `when`(menuRepository.getPdfImage()).thenReturn(pdfResponse)

        // When
        val result = getPdfLocationUseCase().toList()

        // Then
        assertEquals(Response.Loading(true), result[0])
        assertEquals(Response.Success(pdfResponse), result[1])
        assertEquals(false, (result[1] as Response.Success<PdfResponse>).data.isError)
        assertEquals(pdfResponse.base64Data, (result[1] as Response.Success<PdfResponse>).data.base64Data)
        assertEquals(Response.Loading(false), result[2])
    }

    @Test
    fun `when invoke should return error when response is an error`() = runTest {
        // Given
        val errorResponse = PdfResponse(
            isError = true
        )
        `when`(menuRepository.getPdfImage()).thenReturn(errorResponse)

        // When
        val result = getPdfLocationUseCase().toList()

        // Then
        assertEquals(Response.Loading(true), result[0])
        assertEquals(Response.Error(""), result[1])
        assertEquals(Response.Loading(false), result[2])
    }
}