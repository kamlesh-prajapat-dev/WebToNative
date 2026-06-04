package com.example.webtonative.webview.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.LocalDBError
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.webview.domain.repository.HistoryRepository
import com.example.webtonative.webview.ui.mapper.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val _isHistoryDialogVisible = MutableStateFlow(false)
    val isHistoryDialogVisible: StateFlow<Boolean> get() = _isHistoryDialogVisible.asStateFlow()

    fun onChangeIsLDV() {
        _isHistoryDialogVisible.value = !_isHistoryDialogVisible.value
    }

    val list = repository.getHistory()
        .catch {
            _uiState.value = UiState.Error(
                error = LocalDBError.RoomError.Unknown.asUiText()
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun clearHistory() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            when(val result = repository.deleteAllHistory()) {
                is Result.Error<Boolean, LocalDBError> -> {
                    delay(1000.milliseconds)

                    _uiState.value = UiState.Error(
                        error = result.error.asUiText()
                    )
                }
                is Result.Success<Boolean, LocalDBError> -> {
                    delay(1000.milliseconds)

                    _uiState.value = UiState.Success(
                        data = result.data
                    )
                }
            }
        }
    }
}