package com.example.webtonative.splash.data.repository

import com.example.webtonative.splash.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {

    override fun isUserLogin(): Boolean {
        return auth.currentUser != null
    }
}