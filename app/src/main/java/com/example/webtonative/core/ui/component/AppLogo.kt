package com.example.webtonative.core.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun AppLogo() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF7E84FF), Color(0xFF4FC3F7), Color(0xFF81C784)),
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Monitor and Phone Icon
        Canvas(modifier = Modifier.size(60.dp)) {
            val strokeWidth = 3.dp.toPx()
            val color = Color.White

            // Monitor
            drawRoundRect(
                color = color,
                topLeft = Offset(5.dp.toPx(), 10.dp.toPx()),
                size = Size(40.dp.toPx(), 28.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
                style = Stroke(width = strokeWidth)
            )
            // Monitor stand
            drawLine(
                color = color,
                start = Offset(20.dp.toPx(), 38.dp.toPx()),
                end = Offset(30.dp.toPx(), 38.dp.toPx()),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = color,
                start = Offset(15.dp.toPx(), 42.dp.toPx()),
                end = Offset(35.dp.toPx(), 42.dp.toPx()),
                strokeWidth = strokeWidth
            )

            // Phone (overlapping)
            drawRoundRect(
                color = color,
                topLeft = Offset(30.dp.toPx(), 25.dp.toPx()),
                size = Size(20.dp.toPx(), 30.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
                style = Stroke(width = strokeWidth)
            )
            // Phone screen line
            drawLine(
                color = color,
                start = Offset(35.dp.toPx(), 50.dp.toPx()),
                end = Offset(45.dp.toPx(), 50.dp.toPx()),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}