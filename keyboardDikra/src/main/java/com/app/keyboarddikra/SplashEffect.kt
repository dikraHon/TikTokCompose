package com.app.keyboarddikra

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun SplashEffect(value: Float) {
    val colors = LocalKeyboardColors.current
    if (value > 0f && value < KeyboardAnimations.SPLASH_MAX_VALUE) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val progress = value / KeyboardAnimations.SPLASH_MAX_VALUE
            drawCircle(
                color = colors.splash.copy(alpha = 1f - progress),
                radius = (size.minDimension * 0.8f) * value,
                style = Stroke(width = KeyboardDimens.SplashStrokeWidth.toPx())
            )
        }
    }
}
