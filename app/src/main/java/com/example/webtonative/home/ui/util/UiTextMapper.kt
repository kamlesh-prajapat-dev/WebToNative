package com.example.webtonative.home.ui.util

import com.example.webtonative.R
import com.example.webtonative.core.domain.util.error.ValidationError
import com.example.webtonative.core.ui.util.UiText

fun ValidationError.asUiText(): UiText {
    return when(this) {
        ValidationError.UrlVE.Empty -> UiText.StringResource(
            R.string.error_empty_url
        )
        ValidationError.UrlVE.Invalid -> UiText.StringResource(R.string.error_invalid_url)
    }
}