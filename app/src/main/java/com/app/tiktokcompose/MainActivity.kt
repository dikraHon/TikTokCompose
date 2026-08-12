package com.app.tiktokcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.app.tiktokcompose.testingKeyboard.TestScreenKeyBoard
import com.app.tiktokcompose.ui.theme.TikTokComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TikTokComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                  TestScreenKeyBoard()
                }
            }
        }
    }
}
