package com.example.webtonative.core.domain.util.error

sealed interface FirebaseError: Error {
    enum class AuthError: FirebaseError {
        UNKNOWN
    }
}