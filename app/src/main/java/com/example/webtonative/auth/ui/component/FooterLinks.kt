package com.example.webtonative.auth.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun FooterLinks() {
    val annotatedString = buildAnnotatedString {
        append("By continuing, you agree to our ")

        pushStringAnnotation(tag = "terms", annotation = "terms")
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Terms of Service")
        }
        pop()
        append("\nand ")

        pushStringAnnotation(tag = "privacy", annotation = "privacy")
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        pop()
    }

    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
        textAlign = TextAlign.Center,
        lineHeight = 20.sp
    )
}