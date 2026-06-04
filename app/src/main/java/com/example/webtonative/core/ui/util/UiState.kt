package com.example.webtonative.core.ui.util

sealed interface UiState {
    object Idle: UiState
    object Loading: UiState
    data class Success<out D>(
        val data: D
    ): UiState
    data class Error(
        val error: UiText,
        val flag: Boolean = false
    ): UiState
}