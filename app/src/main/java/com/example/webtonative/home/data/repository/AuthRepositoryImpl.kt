package com.example.webtonative.home.data.repository

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.ClearCredentialException
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.Error
import com.example.webtonative.core.domain.util.error.SignInWithGoogleError
import com.example.webtonative.home.domain.repository.AuthRepository
import com.example.webtonative.util.logger.Logger
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    private val logger: Logger
) : AuthRepository {

    private companion object {
        const val TAG = "AuthRepositoryImpl"
    }

    override suspend fun logout(): Result<Boolean, SignInWithGoogleError> {

        auth.signOut()

        // When a user signs out, clear the current user credential state from all credential providers.
        return try {
            val clearRequest = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(clearRequest)

            Result.Success(
                data = true
            )
        } catch (e: ClearCredentialException) {
            logger.e(
                tag = TAG,
                t = e,
                message = "Couldn't clear user credentials: ${e.localizedMessage}"
            )
            Result.Error(
                error = SignInWithGoogleError.ClearCredential
            )
        } catch (e: Exception) {
            logger.e(
                tag = TAG,
                t = e,
                message = "Unknown Error: ${e.localizedMessage}"
            )
            Result.Error(
                error = SignInWithGoogleError.Unknown
            )
        }
    }
}