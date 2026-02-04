package com.skr.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.skr.game.ui.SKRApp
import com.skr.game.ui.theme.SKRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getDecorWindow(this).isNavigationBarContrastEnforced = false
        setContent {
            SKRTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SKRApp()
                }
            }
        }
    }
}
