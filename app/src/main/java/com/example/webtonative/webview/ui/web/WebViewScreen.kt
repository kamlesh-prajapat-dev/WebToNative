package com.example.webtonative.webview.ui.web

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webtonative.core.ui.component.dialog.GenericDialog
import com.example.webtonative.core.ui.component.loader.Loader
import com.example.webtonative.core.ui.util.UiState
import com.example.webtonative.util.NetworkUtils

@Composable
fun WebViewScreen(
    url: String,
    navigateToBack: (String?) -> Unit,
    viewModel: WebViewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val currentUrl by viewModel.currentUrl.collectAsStateWithLifecycle()
    val noInternet by viewModel.noInternet.collectAsStateWithLifecycle()

    val webView = remember {
        WebView(context).apply {

            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)

                    viewModel.onChangeLoading()
                    url?.let { viewModel.updateUrl(url) }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    viewModel.onChangeLoading(false)
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)

                    title?.let { viewModel.saveToHistory(title = it) }
                }
            }
        }
    }

    when(uiState) {
        is UiState.Error -> {
            Toast.makeText(LocalContext.current, (uiState as UiState.Error).error.asString(), Toast.LENGTH_LONG).show()
        }
        UiState.Idle -> Unit
        UiState.Loading -> Loader(modifier = Modifier.fillMaxSize())
        is UiState.Success<*> -> Unit
    }

    LaunchedEffect(url) {
        if (NetworkUtils.isInternetAvailable(context = context)) {
            webView.loadUrl(url)
            viewModel.updateUrl(url)
        } else {
            viewModel.onChangeNoInternet(true)
        }
    }

    BackHandler {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            navigateToBack(url)
        }
    }

    Scaffold(
        topBar = {
            BrowserTopBar(
                url = currentUrl,
                onCloseClick = {
                    navigateToBack(null)
                },
                onBackClick = {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        navigateToBack(url)
                    }
                }
            )
        }
    ) { paddingValues ->

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        AndroidView(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            factory = { webView }
        )
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Loader()
        }
    }

    if(noInternet) {
        GenericDialog(
            title = "No Internet Connection.",
            text = "Please check your network connection and try again.",
            onDismiss = { viewModel.onChangeNoInternet(false) },
            onConfirm = { viewModel.onChangeNoInternet(false); webView.loadUrl(url); viewModel.updateUrl(url) },
            confirmLabel = "OK",
            dismissLabel = ""
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserTopBar(
    url: String,
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = .1f
                    )
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = url,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                shape = RoundedCornerShape(8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Icon.",
                )
            }
        },
        actions = {
            IconButton(
                onClick = onCloseClick,
                shape = RoundedCornerShape(8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon.",
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
