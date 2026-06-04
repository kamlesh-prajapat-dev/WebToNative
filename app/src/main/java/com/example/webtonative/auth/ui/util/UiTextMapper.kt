package com.example.webtonative.auth.ui.util

import com.example.webtonative.R
import com.example.webtonative.core.domain.util.error.SignInWithGoogleError
import com.example.webtonative.core.ui.util.UiText
import com.example.webtonative.core.ui.util.UiText.*

fun SignInWithGoogleError.asUiText(): UiText {
    return when(this) {
        SignInWithGoogleError.Cancellation -> StringResource(R.string.error_cancellation)
        SignInWithGoogleError.GetCredential -> StringResource(R.string.error_get_credential)
        SignInWithGoogleError.GetCustomCredential -> StringResource(R.string.error_get_credential_custom)
        SignInWithGoogleError.NoCredential -> StringResource(R.string.error_no_credential)
        SignInWithGoogleError.Parsing -> StringResource(R.string.error_parsing)
        SignInWithGoogleError.Unknown -> StringResource(R.string.error_unknown)
        SignInWithGoogleError.ClearCredential -> StringResource(R.string.error_clear_credential)
    }
}