package com.example.webtonative.auth.ui

import android.content.Context
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webtonative.R
import com.example.webtonative.auth.domain.handler.SignInRequestHandler
import com.example.webtonative.auth.domain.repository.AuthRepository
import com.example.webtonative.auth.ui.util.asUiText
import com.example.webtonative.core.di.qualifier.LoginRepo
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.FirebaseError
import com.example.webtonative.core.domain.util.error.SignInWithGoogleError
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.core.ui.util.UiText
import com.example.webtonative.core.ui.util.mapper.asUiText
import com.example.webtonative.util.logger.Logger
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    @param:LoginRepo private val repository: AuthRepository,
    private val handler: SignInRequestHandler,
    private val logger: Logger
) : ViewModel() {

    private companion object {
        const val TAG = "LoginViewModel"
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val _noInternet = MutableStateFlow(false)
    val noInternet: StateFlow<Boolean> get() = _noInternet.asStateFlow()

    fun onChangeNoInternet(flag: Boolean) {
        _noInternet.value = flag
    }

    private val _isLoaderVisible = MutableStateFlow(false)
    val isLoaderVisible: StateFlow<Boolean> get() = _isLoaderVisible.asStateFlow()

    fun onChangeLoaderVisible(
        flag: Boolean
    ) {
        _isLoaderVisible.value = flag
    }

    fun signIn(
        request: GetCredentialRequest,
        context: Context
    ) {
        viewModelScope.launch {

            _uiState.value = UiState.Loading

            when (val result = handler.requestHandler(
                request = request,
                context = context
            )) {
                is Result.Error<GetCredentialResponse, SignInWithGoogleError> -> {
                    when (val error = result.error) {
                        SignInWithGoogleError.NoCredential -> _uiState.value = UiState.Error(
                            error = error.asUiText(),
                            flag = true
                        )

                        else -> {
                            _uiState.value = UiState.Error(
                                error = error.asUiText(),
                                flag = false
                            )
                        }
                    }
                }

                is Result.Success<GetCredentialResponse, SignInWithGoogleError> -> {
                    _uiState.value = UiState.Idle
                    when (val credential = result.data.credential) {
                        is CustomCredential -> {
                            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                try {
                                    val googleIdTokenCredential = GoogleIdTokenCredential
                                        .createFrom(credential.data)

                                    _uiState.value = UiState.Loading
                                    repository.signIn(
                                        idToken = googleIdTokenCredential.idToken,
                                        onComplete = { result ->
                                            when(result) {
                                                is Result.Error<Boolean, FirebaseError> -> {
                                                    _uiState.value = UiState.Error(
                                                        error = result.error.asUiText(),
                                                        flag = false
                                                    )
                                                }
                                                is Result.Success<Boolean, FirebaseError> -> {
                                                    _uiState.value = UiState.Success(
                                                        data = result.data
                                                    )
                                                }
                                            }
                                        }
                                    )
                                } catch (e: GoogleIdTokenParsingException) {
                                    logger.e(TAG, "Received an invalid google id token response", e)
                                    _uiState.value = UiState.Error(
                                        error = UiText.StringResource(R.string.error_unknown)
                                    )
                                }
                            } else {
                                // Catch any unrecognized credential type here.
                                logger.e(TAG, "Unexpected type of credential")
                                _uiState.value = UiState.Error(
                                    error = UiText.StringResource(R.string.error_unknown)
                                )
                            }
                        }

                        else -> {
                            // Catch any unrecognized credential type here.
                            logger.e(TAG, "Unexpected type of credential")
                            _uiState.value = UiState.Error(
                                error = UiText.StringResource(R.string.error_unknown)
                            )
                        }
                    }
                }
            }
        }
    }
}