package com.example.webtonative.core.domain.util.error

sealed interface ValidationError: Error {

    enum class UrlVE: ValidationError {
        Empty,
        Invalid
    }
}