package com.example.webtonative.home.domain.repository

import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.SignInWithGoogleError

interface AuthRepository {

    suspend fun logout(): Result<Boolean, SignInWithGoogleError>
}