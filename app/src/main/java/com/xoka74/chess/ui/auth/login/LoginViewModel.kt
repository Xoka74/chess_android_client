package com.xoka74.chess.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xoka74.chess.data.auth.AuthService
import com.xoka74.chess.data.auth.UserInfoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userInfoManager: UserInfoManager,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(LoginUiState())
    val uiState = mutableUiState.asStateFlow()

    init {
        mutableUiState.update { it.copy(isLogging = true) }
        viewModelScope.launch {
            val body = authService.verify(userInfoManager.getToken(userInfoManager.accessTokenKey))
            val isLoggedIn = body.code() == 200
            mutableUiState.update { it.copy(isLogging = false, isLoggingSuccessful = isLoggedIn) }
        }
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableUiState.update { it.copy(isLogging = true) }
            val loginResponse =
                authService.login("xoka74", "1234")
            val body = loginResponse.body()
            if (loginResponse.isSuccessful && body != null) {
                userInfoManager.saveToken(userInfoManager.accessTokenKey, body.accessToken)
                userInfoManager.saveToken(userInfoManager.refreshTokenKey, body.refreshToken)
                mutableUiState.update { it.copy(isLogging = false, isLoggingSuccessful = true) }
            } else {
                mutableUiState.update { it.copy(isLogging = false, isLoggingSuccessful = false) }
            }
        }
    }

    fun enterUsername(username: String) {
        mutableUiState.update {
            it.copy(username = username)
        }
    }

    fun enterPassword(password: String) {
        mutableUiState.update {
            it.copy(username = password)
        }
    }

    data class LoginUiState(
        val isLogging: Boolean = false,
        val isLoggingSuccessful: Boolean = false,
        val username: String = "",
        val password: String = "",
    )
}