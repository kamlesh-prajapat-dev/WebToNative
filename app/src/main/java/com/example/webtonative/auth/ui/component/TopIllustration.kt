package com.example.webtonative.auth.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

@Composable
fun TopIllustration() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        // Web Browser Icon (Purple/Blue)
        Box(
            modifier = Modifier
                .size(70.dp, 45.dp)
                .border(2.dp, Color(0xFF534C69), RoundedCornerShape(8.dp))
                .padding(4.dp)
        ) {
            Column {
                Row(modifier = Modifier.padding(bottom = 4.dp)) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFF534C69))
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF534C69))
                )
            }
        }

        // Dashed Arrow
        Canvas(
            modifier = Modifier
                .size(50.dp, 20.dp)
                .padding(horizontal = 8.dp)
        ) {
            val strokeWidth = 2.dp.toPx()
            val dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

            drawLine(
                color = Color(0xFF534C69),
                start = Offset(0f, size.height / 2),
                end = Offset(size.width - 8f, size.height / 2),
                strokeWidth = strokeWidth,
                pathEffect = dashPathEffect
            )

            // Arrow head
            drawLine(
                color = Color(0xFF534C69),
                start = Offset(size.width - 16f, size.height / 2 - 8f),
                end = Offset(size.width - 8f, size.height / 2),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = Color(0xFF534C69),
                start = Offset(size.width - 16f, size.height / 2 + 8f),
                end = Offset(size.width - 8f, size.height / 2),
                strokeWidth = strokeWidth
            )
        }

        // Phone Icon (Green)
        Box(
            modifier = Modifier
                .size(35.dp, 60.dp)
                .border(2.dp, Color(0xFF4C695E), RoundedCornerShape(8.dp))
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color(0xFF4C695E).copy(alpha = 0.5f), RoundedCornerShape(2.dp))
            )
        }
    }
}