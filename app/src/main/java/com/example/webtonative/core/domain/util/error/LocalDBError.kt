package com.example.webtonative.core.domain.util.error

sealed interface LocalDBError: Error {

    enum class RoomError: LocalDBError {
        ConstraintViolation,
        DiskFull,
        Corrupted,
        Unknown
    }
}