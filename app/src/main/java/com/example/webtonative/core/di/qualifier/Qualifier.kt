package com.example.webtonative.core.di.qualifier

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SplashRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeRepo