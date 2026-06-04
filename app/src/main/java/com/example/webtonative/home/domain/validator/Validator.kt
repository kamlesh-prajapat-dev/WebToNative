package com.example.webtonative.home.domain.validator

import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.ValidationError
import java.net.URISyntaxException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Validator @Inject constructor() {

    fun validateUrl(
        url: String
    ): Result<Unit, ValidationError> {
        if (url.isBlank()) return Result.Error(
            error = ValidationError.UrlVE.Empty
        )

        return try {
            val uri = java.net.URI(url)
            uri.scheme in listOf("http", "https") &&
                    !uri.host.isNullOrBlank()

            Result.Success(
                data = Unit
            )
        } catch (_: URISyntaxException) {
            Result.Error(
                error = ValidationError.UrlVE.Invalid
            )
        } catch (_: Exception) {
            Result.Error(
                error = ValidationError.UrlVE.Invalid
            )
        }
    }
}