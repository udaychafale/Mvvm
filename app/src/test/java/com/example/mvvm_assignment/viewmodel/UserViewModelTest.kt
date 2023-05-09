package com.example.mvvm_assignment.viewmodel

import app.cash.turbine.test
import com.example.mvvm_assignment.model.User
import com.example.mvvm_assignment.repositories.UserRepository
import com.example.mvvm_assignment.utils.DispatcherProvider
import com.example.mvvm_assignment.utils.TestDispatcherProvider
import com.example.mvvm_assignment.utils.UiState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserViewModelTest {

    @Mock
    private lateinit var userRepository: UserRepository


    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun givenServerResponse200_whenGetUser_shouldReturnSuccess() {
        runTest {
            Mockito.doReturn(flowOf(emptyList<User>())).`when`(userRepository).getUsers()
            val viewModel =
                UserViewModel(userRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<User>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            Mockito.verify(userRepository).getUsers()
        }
    }

    @Test
    fun givenServerResponseError_whenGetUser_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"
            Mockito.doReturn(flow<List<User>> {
                throw IllegalStateException(errorMessage)
            }).`when`(userRepository).getUsers()

            val viewModel =
                UserViewModel(userRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            Mockito.verify(userRepository).getUsers()
        }
    }

}