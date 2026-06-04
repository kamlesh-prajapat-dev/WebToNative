package com.example.webtonative.auth.domain.handler

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.SignInWithGoogleError
import com.example.webtonative.util.logger.Logger
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import javax.inject.Inject

class SignInRequestHandler @Inject constructor(
    private val credentialManager: CredentialManager,
    private val logger: Logger
) {

    private companion object {
        const val TAG = "SignInRequestHandler"
    }

    suspend fun requestHandler(
        request: GetCredentialRequest,
        context: Context
    ): Result<GetCredentialResponse, SignInWithGoogleError> {
        return try {
            // The getCredential is called to request a credential from Credential Manager.
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )

            Result.Success(
                data = result
            )

        } catch (e: NoCredentialException) {
            logger.e(TAG, message = e.message ?: "Unknown Error.", t = e)

            Result.Error(
                error = SignInWithGoogleError.NoCredential
            )
        }
        catch (e: GetCredentialException) {
            logger.e(TAG, message = e.message ?: "Unknown Error.", t = e)
            Result.Error(
                error = SignInWithGoogleError.GetCredential
            )
        } catch (e: GoogleIdTokenParsingException) {
            logger.e(TAG, message = e.message ?: "Unknown Error.", t = e)
            Result.Error(
                error = SignInWithGoogleError.Parsing
            )
        }  catch (e: GetCredentialCustomException) {
            logger.e(TAG, message = e.message ?: "Unknown Error.", t = e)

            Result.Error(
                error = SignInWithGoogleError.GetCustomCredential
            )
        } catch (e: GetCredentialCancellationException) {
            logger.e(TAG, message = e.message ?: "Unknown Error.", t = e)

            Result.Error(
                error = SignInWithGoogleError.Cancellation
            )
        } catch (e: Exception) {
            logger.e(TAG, message = e.message ?: "Unknown Error.", t = e)

            Result.Error(
                error = SignInWithGoogleError.Unknown
            )
        }
    }
}