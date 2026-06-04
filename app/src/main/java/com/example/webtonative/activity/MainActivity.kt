package com.example.webtonative.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.webtonative.core.ui.theme.WebToNativeTheme
import com.example.webtonative.navigate.WebToNativeApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebToNativeTheme {
                WebToNativeApp()
            }
        }
    }
}