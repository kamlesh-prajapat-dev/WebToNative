package com.example.webtonative.webview.ui.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.LocalDBError
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.webview.domain.model.HistoryItem
import com.example.webtonative.webview.domain.repository.HistoryRepository
import com.example.webtonative.webview.ui.mapper.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val repository: HistoryRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()
    fun onChangeLoading(flag: Boolean? = null) {
        _isLoading.value = flag ?: !_isLoading.value
    }

    private val _currentUrl = MutableStateFlow("")
    val currentUrl: StateFlow<String> get() = _currentUrl.asStateFlow()

    fun updateUrl(url: String) {
        if (_currentUrl.value != url) {
            _currentUrl.value = url
        }
    }

    fun saveToHistory(title: String) {
        viewModelScope.launch {
            when(val result = repository.saveHistory(
                item = HistoryItem(
                    url = _currentUrl.value,
                    id = 0,
                    title = title,
                    visitCount = 1,
                    lastVisitTime = "",
                    iconInitial = title[0]
                )
            )) {
                is Result.Error<Boolean, LocalDBError> -> {
                    _uiState.value = UiState.Error(
                        error = result.error.asUiText()
                    )
                }
                is Result.Success<Boolean, LocalDBError> -> {
                    _uiState.value = UiState.Success(
                        data = result.data
                    )
                }
            }
        }
    }
}
