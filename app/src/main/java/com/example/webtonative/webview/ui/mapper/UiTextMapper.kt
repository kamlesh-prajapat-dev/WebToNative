package com.example.webtonative.webview.ui.mapper

import com.example.webtonative.R
import com.example.webtonative.core.domain.util.error.LocalDBError
import com.example.webtonative.core.ui.util.UiText
import com.example.webtonative.core.ui.util.UiText.*

fun LocalDBError.asUiText(): UiText {
    return when(this) {
        LocalDBError.RoomError.Unknown -> StringResource(
            R.string.error_unknown
        )

        LocalDBError.RoomError.ConstraintViolation -> StringResource(R.string.error_constraint_local)
        LocalDBError.RoomError.DiskFull -> StringResource(R.string.error_full_disk)
        LocalDBError.RoomError.Corrupted -> StringResource(R.string.error_corrupted)
    }
}