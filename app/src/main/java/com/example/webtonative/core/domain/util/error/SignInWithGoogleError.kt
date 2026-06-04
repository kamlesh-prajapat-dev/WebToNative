package com.example.webtonative.core.domain.util.error

sealed interface SignInWithGoogleError : Error {
    object GetCredential: SignInWithGoogleError
    object Parsing: SignInWithGoogleError
    object NoCredential: SignInWithGoogleError
    object GetCustomCredential: SignInWithGoogleError
    object Cancellation: SignInWithGoogleError
    object Unknown: SignInWithGoogleError
    object ClearCredential: SignInWithGoogleError
}