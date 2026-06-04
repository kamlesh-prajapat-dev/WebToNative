package com.example.webtonative.auth.data.repository.mapper

import com.example.webtonative.core.domain.util.error.FirebaseError
import com.example.webtonative.core.domain.util.error.mapper.ErrorMapper
import com.example.webtonative.util.logger.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseErrorMapper @Inject constructor(
    private val logger: Logger
): ErrorMapper {

    private companion object {
        const val TAG = "FirebaseErrorMapper"
    }

    override fun mapError(t: Throwable?): FirebaseError {
        logger.e(
            tag = TAG,
            t = t,
            message = t?.message ?: "UnKnown Error"
        )
        return when(t) {
            is Exception -> FirebaseError.AuthError.UNKNOWN
            else -> FirebaseError.AuthError.UNKNOWN
        }
    }
}