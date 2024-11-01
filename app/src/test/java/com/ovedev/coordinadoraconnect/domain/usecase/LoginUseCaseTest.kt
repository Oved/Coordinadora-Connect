package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
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
class LoginUseCaseTest {

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var userDBRepository: UserDBRepository

    @Mock
    private lateinit var firebaseRepository: FirebaseRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginUseCase(authRepository, userDBRepository, firebaseRepository)
    }

    @Test
    fun `when invoke should return success when login is successful`() = runTest {
        // Given
        val username = "OvedTest"
        val password = "12345"
        val loginResponse = LoginResponse(
            isError = false,
            dataUserName = "Oved Test",
            validationPeriod = 4
        )

        val userEntity = UserEntity(
            userId = username,
            userName = "Oved Test",
            registerDate = "2024",
            validationPeriod = 4
        )

        `when`(authRepository.login(username, password)).thenReturn(loginResponse)
        `when`(userDBRepository.saveUser(userEntity)).thenReturn(Unit)
        `when`(firebaseRepository.saveUser(username, "Oved Test", 4)).thenReturn(true)

        // When
        val result = loginUseCase(username, password).toList()

        // Then
        assertEquals(Response.Loading(true), result[0])
        assertEquals(Response.Success(loginResponse), result[1])
        assertEquals(Response.Loading(false), result[2])
    }

    @Test
    fun `when invoke should return error when login fails`() = runTest {
        // Given
        val username = "OvedTest"
        val password = "12345"
        val errorMessage = "Invalid credentials"
        val loginResponse = LoginResponse(
            isError = true,
            message = errorMessage
        )

        `when`(authRepository.login(username, password)).thenReturn(loginResponse)

        // When
        val result = loginUseCase(username, password).toList()

        // Then
        assertEquals(Response.Loading(true), result[0])
        assertEquals(Response.Error(errorMessage), result[1])
        assertEquals(Response.Loading(false), result[2])
    }
}