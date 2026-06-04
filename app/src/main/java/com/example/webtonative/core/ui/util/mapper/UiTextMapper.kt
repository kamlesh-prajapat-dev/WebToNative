package com.example.webtonative.core.ui.util.mapper

import com.example.webtonative.R
import com.example.webtonative.core.domain.util.error.FirebaseError
import com.example.webtonative.core.ui.util.UiText

fun FirebaseError.asUiText(): UiText {
    return when(this) {
        FirebaseError.AuthError.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
    }
}