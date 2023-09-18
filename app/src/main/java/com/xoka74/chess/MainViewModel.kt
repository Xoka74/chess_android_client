package com.xoka74.chess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xoka74.chess.domain.models.Game
import com.xoka74.chess.domain.repositories.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(DashboardUiState())

    val uiState = mutableUiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchPendingGame()
        }
    }

    private suspend fun fetchPendingGame() {
        mutableUiState.update {
            it.copy(isLoading = true, game = null)
        }
        try {
            val game = gameRepository.getPendingGame()
            mutableUiState.update {
                it.copy(isLoading = false, game = game)
            }
        } catch (exc: UnknownHostException) {
            delay(2000)
            fetchPendingGame()
        }
    }

    data class DashboardUiState(
        val isLoading: Boolean = true,
        val game: Game? = null,
    )
}


