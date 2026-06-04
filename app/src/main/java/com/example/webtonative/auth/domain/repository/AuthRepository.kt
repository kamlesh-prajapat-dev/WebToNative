package com.example.webtonative.auth.domain.repository

import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.FirebaseError

interface AuthRepository {

    fun signIn(
        idToken: String,
        onComplete: (Result<Boolean, FirebaseError>) -> Unit
    )
}