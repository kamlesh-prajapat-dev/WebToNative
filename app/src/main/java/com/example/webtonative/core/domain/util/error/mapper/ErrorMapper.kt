package com.example.webtonative.core.domain.util.error.mapper

import com.example.webtonative.core.domain.util.error.Error

interface ErrorMapper {

    fun mapError(
        t: Throwable?
    ): Error
}