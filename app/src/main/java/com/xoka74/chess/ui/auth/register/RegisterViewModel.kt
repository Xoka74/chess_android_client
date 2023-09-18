package com.xoka74.chess.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xoka74.chess.data.auth.AuthService
import com.xoka74.chess.data.auth.requests.CreateUserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {


    private val mutableUiState = MutableStateFlow(RegisterUiState())

    val uiState = mutableUiState.asStateFlow()

    fun register() {
        mutableUiState.update { it.copy(isRegistering = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val uiState = mutableUiState.value
            val userRequest = CreateUserRequest(
                uiState.username, uiState.email, uiState.password, uiState.rePassword
            )
            val response = authService.createUser(userRequest)
            if (response.isSuccessful) {
                mutableUiState.update {
                    it.copy(isRegistering = false, isRegisterSuccessful = true)
                }
            }
        }
    }

    data class RegisterUiState(
        val isRegistering: Boolean = false,
        val isRegisterSuccessful: Boolean = false,
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val rePassword: String = "",
    )
}