package com.example.webtonative.auth.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webtonative.auth.ui.component.AppHeader
import com.example.webtonative.auth.ui.component.FooterLinks
import com.example.webtonative.auth.ui.component.GoogleAuthUiProvider
import com.example.webtonative.auth.ui.component.SignInSection
import com.example.webtonative.auth.ui.component.TopIllustration
import com.example.webtonative.core.ui.component.AppLogo
import com.example.webtonative.core.ui.component.dialog.GenericDialog
import com.example.webtonative.core.ui.component.loader.Loader
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.util.NetworkUtils

@Composable
fun LoginScreen(
    webClientId: String,
    onGoogleSignInClick: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val noInternet by viewModel.noInternet.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Top Illustration
            TopIllustration()

            Spacer(modifier = Modifier.height(64.dp))

            // App Logo
            AppLogo()

            Spacer(modifier = Modifier.height(32.dp))

            // App Header
            AppHeader()

            Spacer(modifier = Modifier.weight(1f))

            // Sign In Section
            SignInSection(
                onGoogleSignInClick = {
                    if (NetworkUtils.isInternetAvailable(context = context)) {
                        val request = GoogleAuthUiProvider.getGoogleAuthUi(
                            webClientId = webClientId
                        )

                        viewModel.signIn(
                            request = request,
                            context = context
                        )
                    } else {
                        viewModel.onChangeNoInternet(true)
                    }
                },
                isEnabled = uiState !is UiState.Loading
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Footer
            FooterLinks()
        }
    }

    when (uiState) {
        is UiState.Error -> {
            Toast.makeText(
                context,
                (uiState as UiState.Error).error.asString(context),
                Toast.LENGTH_LONG
            ).show()
        }

        UiState.Idle -> Unit

        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Loader(modifier = Modifier.fillMaxSize())
            }
        }

        is UiState.Success<*> -> {
            onGoogleSignInClick()
        }
    }

    if (noInternet) {
        GenericDialog(
            title = "No Internet Connection.",
            text = "Please check your network connection and try again.",
            onDismiss = { viewModel.onChangeNoInternet(false) },
            onConfirm = { viewModel.onChangeNoInternet(false) },
            dismissLabel = "",
            confirmLabel = "OK"
        )
    }
}