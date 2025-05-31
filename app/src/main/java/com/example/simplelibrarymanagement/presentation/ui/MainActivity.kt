package com.example.simplelibrarymanagement.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.AuthScreen
import com.example.simplelibrarymanagement.presentation.ui.theme.SimpleLibraryManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        setContent {
            SimpleLibraryManagementTheme {
                LibraryApp()
            }
        }
    }
}

@Composable
fun LibraryApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // For now, we'll start with AuthScreen
        // Later this will be managed by navigation and authentication state
        AuthScreen()
    }
}