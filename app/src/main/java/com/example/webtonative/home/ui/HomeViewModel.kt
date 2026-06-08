package com.example.webtonative.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webtonative.auth.ui.util.asUiText
import com.example.webtonative.core.di.qualifier.HomeRepo
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.SignInWithGoogleError
import com.example.webtonative.core.domain.util.error.ValidationError
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.home.domain.repository.AuthRepository
import com.example.webtonative.home.domain.validator.Validator
import com.example.webtonative.home.ui.util.asUiText
import com.example.webtonative.notification.schedular.DailyNotificationSchedular
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param: HomeRepo private val repository: AuthRepository,
    private val validator: Validator,
    private val schedular: DailyNotificationSchedular
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val _isLogoutDialogVisible = MutableStateFlow(false)
    val isLogoutDialogVisible: StateFlow<Boolean> get() = _isLogoutDialogVisible.asStateFlow()

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> get() = _url.asStateFlow()

    fun onChangeUrl(url: String) {
        _url.value = url
    }

    fun onChangeIsLDV() {
        _isLogoutDialogVisible.value = !_isLogoutDialogVisible.value
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when(val result = repository.logout()) {
                is Result.Error<Boolean, SignInWithGoogleError> -> {
                    delay(500.milliseconds)
                    _uiState.value = UiState.Error(
                        error = result.error.asUiText(),
                        flag = false
                    )
                }
                is Result.Success<Boolean, SignInWithGoogleError> -> {
                    delay(500.milliseconds)
                    schedular.cancelSchedular()
                    _uiState.value = UiState.Success(
                        data = result.data
                    )
                }
            }
        }
    }

    fun onOpenAppClick(url: String) {
        when(val result = validator.validateUrl(url)) {
            is Result.Error<Unit, ValidationError> -> {
                _uiState.value = UiState.Error(
                    error = result.error.asUiText(),
                    flag = true
                )
            }
            is Result.Success<Unit, ValidationError> -> {
                _uiState.value = UiState.Success(
                    data = Unit
                )
            }
        }
    }

    fun reset() {
        _uiState.value = UiState.Idle
    }
}