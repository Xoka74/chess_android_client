package com.xoka74.chess.ui.profile.history_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xoka74.chess.domain.models.PaginatedGameList
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
class GameHistoryListViewModel @Inject constructor(
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(GameHistoryListUiState())
    val uiState = mutableUiState.asStateFlow()

    init {
        fetchHistory()
    }

    fun fetchHistory() {
        if (uiState.value.isLoading) return
        viewModelScope.launch {
            try {
                mutableUiState.update { it.copy(isLoading = true) }
                val paginatedList = gameRepository.getGamesHistory(uiState.value.currentPage)
                mutableUiState.update {
                    GameHistoryListUiState(
                        paginatedList=paginatedList,
                        isLoaded = paginatedList?.next == null,
                        isLoading = false,
                    )
                }
            } catch (exc: UnknownHostException) {
                mutableUiState.update { it.copy(isLoading = false) }
                Log.i("FUCK", "FUCK")
                delay(2000)
                fetchHistory()
            }
        }
    }

    data class GameHistoryListUiState(
        val isLoaded: Boolean = false,
        val isLoading: Boolean = false,
        val paginatedList: PaginatedGameList? = null,
        val currentPage : Int = 1,
    )
}