package com.example.webtonative.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webtonative.core.di.qualifier.SplashRepo
import com.example.webtonative.splash.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SplashViewModel @Inject constructor(
    @param:SplashRepo private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashState>(SplashState.Idle)
    val uiState: StateFlow<SplashState> get() = _uiState.asStateFlow()

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            _uiState.value = SplashState.Loading

            delay(2000.milliseconds)

            if (repository.isUserLogin()) {
                _uiState.value = SplashState.Home
            } else {
                _uiState.value = SplashState.Login
            }
        }
    }
}