package com.app.showcode

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun CodeShowcaseScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val codeFiles = remember { CodeLoader.listCodeFiles(context) }
    var selectedFile by remember { mutableStateOf(value = codeFiles.firstOrNull() ?: "") }
    
    val currentCode = remember(key1 = selectedFile) {
        if (selectedFile.isNotEmpty()) {
            CodeLoader.loadFromAssets(context = context, fileName = selectedFile)
        } else {
            "No files found in assets/code/"
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CodeScrollingView(code = currentCode, typingSpeed = 0L)
    }
}
