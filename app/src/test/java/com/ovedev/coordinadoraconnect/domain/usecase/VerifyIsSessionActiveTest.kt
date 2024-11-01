package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
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
class VerifyIsSessionActiveTest {

    @Mock
    private lateinit var userDBRepository: UserDBRepository

    private lateinit var verifyIsSessionActive: VerifyIsSessionActive

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        verifyIsSessionActive = VerifyIsSessionActive(userDBRepository)
    }

    @Test
    fun `when invoke should return true when session is active`() = runTest {
        // Given
        val userEntity = UserEntity(
            userId = "ovedTest",
            userName = "Oved Test",
            registerDate = "2024",
            validationPeriod = 4
        )
        `when`(userDBRepository.getUser()).thenReturn(userEntity)

        // When
        val result = verifyIsSessionActive().toList()

        // Then
        assertEquals(Response.Loading(true), result[0])
        assertEquals(Response.Success(true), result[1])
        assertEquals(true, (result[1] as Response.Success<Boolean>).data)
        assertEquals(Response.Loading(false), result[2])
    }

    @Test
    fun `when invoke should return false when session is inactive`() = runTest {
        // Given
        `when`(userDBRepository.getUser()).thenReturn(null)

        // When
        val result = verifyIsSessionActive().toList()

        // Then
        assertEquals(Response.Loading(true), result[0])
        assertEquals(Response.Success(false), result[1])
        assertEquals(false, (result[1] as Response.Success<Boolean>).data)
        assertEquals(Response.Loading(false), result[2])
    }

}