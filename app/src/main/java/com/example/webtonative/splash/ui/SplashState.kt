package com.example.webtonative.splash.ui

sealed interface SplashState {
    object Idle: SplashState
    object Loading: SplashState
    object Home: SplashState
    object Login: SplashState
}