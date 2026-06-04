package com.example.webtonative.core.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun GenericDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    title: String = "Internet Connection Issue.",
    text: String = "No Internet Connection. Please check your internet and try again.",
    dismissLabel: String = "Cancel",
    confirmLabel: String = "Ok"
) {

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = title
            )
        },

        text = {
            Text(
                text = text
            )
        },

        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = confirmLabel)
            }
        },

        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = dismissLabel)
            }
        }
    )
}