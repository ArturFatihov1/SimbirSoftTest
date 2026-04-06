package com.example.simbirsofttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.simbirsofttest.presentation.AppNavigation
import com.example.simbirsofttest.ui.theme.SimbirSoftTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimbirSoftTestTheme {
                AppNavigation()
            }
        }
    }
}
