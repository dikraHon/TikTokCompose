package com.app.tiktokcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.app.tiktokcompose.codeShow.CodeShowcaseScreen
import com.app.tiktokcompose.testingKeyboard.TestScreenKeyBoard
import com.app.tiktokcompose.ui.theme.TikTokComposeTheme
private val CURRENT_MODE = AppMode.CODE

enum class AppMode {
    KEYBOARD, CODE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TikTokComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (CURRENT_MODE) {
                        AppMode.KEYBOARD -> TestScreenKeyBoard()
                        AppMode.CODE -> CodeShowcaseScreen()
                    }
                }
            }
        }
    }
}
