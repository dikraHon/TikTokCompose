package com.app.keyboarddikra

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SplashEffect(value: Float) {
    if (value > 0f && value < 1.5f) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val progress = value / 1.5f
            drawCircle(
                color = KeyboardColors.Splash.copy(alpha = 1f - progress),
                radius = (size.minDimension * 0.8f) * value,
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}
