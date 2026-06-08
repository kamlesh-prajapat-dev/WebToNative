package com.example.webtonative.auth.ui.component

import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import java.security.SecureRandom
import java.util.Base64

object GoogleAuthUiProvider  {

    fun getGoogleAuthUi(
        webClientId: String
    ) : GetCredentialRequest {

        // Create a Google ID option with filtering by authorized accounts enabled.
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setNonce(generateSecureRandomNonce())
            .build()

        // Create a credential request with the Google ID option.
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return request
    }

    private fun generateSecureRandomNonce(byteLength: Int = 32): String {
        val randomBytes = ByteArray(byteLength)
        SecureRandom.getInstanceStrong().nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }
}
