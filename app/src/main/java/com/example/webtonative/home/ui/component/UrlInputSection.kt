package com.example.webtonative.home.ui.component

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UrlInputSection(
    url: String,
    validationError: String,
    isShowValidationError: Boolean,
    onPastClick: (String) -> Unit,
    onUrlClick: (String) -> Unit
) {
    val context = LocalContext.current
    Column {
        Text(
            text = "Website URL",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            value = url,
            onValueChange = {
                onUrlClick(it)
            },
            placeholder = {
                Text(
                    text = "https://yourwebsite.com"
                )
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Language Icon.",
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                Button(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription?.hasMimeType("text/plain") == true) {
                            val pasteData = clipboard.primaryClip?.getItemAt(0)?.text?.toString() ?: ""

                            if (pasteData.isNotEmpty()) {
                                onPastClick(pasteData)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = .1f
                        ),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .padding(end = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Paste",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            },
            supportingText = {
                if (isShowValidationError) {
                    Text(
                        text = validationError,
                        fontSize = 12.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "We'll wrap it into a native Android & iOS app.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
