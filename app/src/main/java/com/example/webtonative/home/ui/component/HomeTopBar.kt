package com.example.webtonative.home.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onHistoryClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        title = {
            Column(
                modifier = Modifier
                    .padding(11.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "WebToNative",
                    fontSize = 16.sp,
                    lineHeight = 1.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "by Orufy",
                    fontSize = 12.sp
                )
            }
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF7E84FF),
                                Color(0xFF4FC3F7),
                                Color(0xFF81C784)
                            ),
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(24.dp)) {
                    val strokeWidth = 2.dp.toPx()
                    val color = Color.White
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(2.dp.toPx(), 4.dp.toPx()),
                        size = Size(16.dp.toPx(), 11.dp.toPx()),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx()),
                        style = Stroke(width = strokeWidth)
                    )
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(12.dp.toPx(), 9.dp.toPx()),
                        size = Size(8.dp.toPx(), 12.dp.toPx()),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx()),
                        style = Stroke(width = strokeWidth)
                    )
                }
            }
        },
        actions = {
            Row {
                IconButton(
                    onClick = onHistoryClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = .1f
                        ),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "Close",
                    )
                }

                IconButton(
                    onClick = onLogoutClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = .1f
                        ),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = "Close"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
