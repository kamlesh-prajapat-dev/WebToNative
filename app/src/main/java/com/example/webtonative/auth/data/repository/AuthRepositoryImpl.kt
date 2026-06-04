package com.example.webtonative.auth.data.repository

import com.example.webtonative.auth.domain.repository.AuthRepository
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.FirebaseError
import com.example.webtonative.core.domain.util.error.mapper.ErrorMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val mapper: ErrorMapper
) : AuthRepository {

    override fun signIn(
        idToken: String,
        onComplete: (Result<Boolean, FirebaseError>) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(
                        Result.Success(
                            data = true
                        )
                    )
                } else {
                    onComplete(
                        Result.Error(
                            error = mapper.mapError(task.exception) as FirebaseError
                        )
                    )
                }
            }
    }
}