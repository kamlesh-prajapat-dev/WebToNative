package com.example.webtonative.home.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webtonative.R
import com.example.webtonative.core.ui.component.dialog.GenericDialog
import com.example.webtonative.core.ui.component.loader.Loader
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.home.domain.model.CarouselItem
import com.example.webtonative.home.ui.component.HomeTopBar
import com.example.webtonative.home.ui.component.ImageCarousel
import com.example.webtonative.home.ui.component.OpenAppButton
import com.example.webtonative.home.ui.component.UrlInputSection

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToWebView: (String) -> Unit,
    navigateToLogin: () -> Unit,
    navigateToHistory: () -> Unit,
    lastUrl: String? = null
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLogoutDialogVisible by viewModel.isLogoutDialogVisible.collectAsStateWithLifecycle()
    val url by viewModel.url.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        isLogoutDialogVisible = isLogoutDialogVisible,
        url = url,
        onUrlChange = { viewModel.onChangeUrl(it) },
        onOpenAppClick = { viewModel.onOpenAppClick(url) },
        onHistoryClick = navigateToHistory,
        onLogoutClick = { viewModel.onChangeIsLDV() },
        onLogoutConfirm = {
            viewModel.logout()
            viewModel.onChangeIsLDV()
        },
        onLogoutDismiss = { viewModel.onChangeIsLDV() },
        onNavigateToLogin = navigateToLogin,
        onNavigateToWebView = { navigateToWebView(url); viewModel.reset() },
        lastUrl = lastUrl
    )
}

@Composable
fun HomeScreenContent(
    uiState: UiState,
    isLogoutDialogVisible: Boolean,
    url: String,
    onUrlChange: (String) -> Unit,
    onOpenAppClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLogoutConfirm: () -> Unit,
    onLogoutDismiss: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToWebView: () -> Unit,
    lastUrl: String? = null
) {
    // Observe lastUrl for returned URL
    LaunchedEffect(lastUrl) {
        if (!lastUrl.isNullOrBlank()) {
            onUrlChange(lastUrl)
        } else {
            onUrlChange("")
        }
    }

    if (isLogoutDialogVisible) {
        GenericDialog(
            title = "Logout Action",
            text = "Are you sure you want to logout form account?",
            onDismiss = onLogoutDismiss,
            onConfirm = onLogoutConfirm,
            confirmLabel = "Confirm",
            dismissLabel = "Cancel"
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar(
                onHistoryClick = onHistoryClick,
                onLogoutClick = onLogoutClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                ImageCarousel(
                    items = listOf(
                        CarouselItem(1, R.drawable.img_1, "donut"),
                        CarouselItem(0, R.drawable.img_2, "cupcake"),
                        CarouselItem(2, R.drawable.img_3, "eclair")
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                UrlInputSection(
                    url = url,
                    validationError = if (uiState is UiState.Error && uiState.flag) uiState.error.asString() else "",
                    isShowValidationError = uiState is UiState.Error && uiState.flag,
                    onPastClick = {
                        onUrlChange(it)
                    }
                ) { newUrl ->
                    onUrlChange(newUrl)
                }

                Spacer(modifier = Modifier.weight(1f))

                OpenAppButton {
                    onOpenAppClick()
                }
            }
        }
    }

    when (uiState) {
        is UiState.Error -> {
            Toast.makeText(LocalContext.current, uiState.error.asString(), Toast.LENGTH_LONG).show()
        }
        UiState.Idle -> Unit
        UiState.Loading -> Loader()
        is UiState.Success<*> -> {
            val data = uiState.data
            if (data is Boolean && data) {
                onNavigateToLogin()
            } else if (data is Unit) {
                onNavigateToWebView()
            }
        }
    }
}

