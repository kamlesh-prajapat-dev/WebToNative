package com.example.webtonative.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.webtonative.BuildConfig
import com.example.webtonative.auth.ui.LoginScreen
import com.example.webtonative.home.ui.HomeScreen
import com.example.webtonative.splash.ui.SplashScreen
import com.example.webtonative.webview.ui.history.HistoryScreen
import com.example.webtonative.webview.ui.web.WebViewScreen


@Composable
fun WebToNativeApp(
    navController: NavHostController = rememberNavController(),
    webClientId: String = BuildConfig.WEB_CLIENT_ID,
) {

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Splash) {
                            inclusive = true
                        }
                    }
                },
                navigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo(Splash) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Login> {
            LoginScreen(
                webClientId = webClientId,
                onGoogleSignInClick = {
                    navController.navigate(Home) {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                navigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo<Home> {
                            inclusive = true
                        }
                    }
                },
                navigateToWebView = { url ->
                    navController.navigate(WebView(url))
                },
                navigateToHistory = {
                    navController.navigate(History)
                },
                lastUrl = navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.get<String>("last_url")
            )
        }

        composable<WebView> {
            val route = it.toRoute<WebView>()

            WebViewScreen(
                url = route.url,
                navigateToBack = { lastUrl ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("last_url", lastUrl)
                    navController.popBackStack()
                }
            )
        }

        composable<History> {
            HistoryScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onItemClick = {
                    navController.navigate(WebView(it.url))
                }
            )
        }
    }
}