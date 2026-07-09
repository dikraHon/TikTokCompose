package com.app.keyboarddikra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun KeyContent(
    text: String,
    isPressed: Boolean,
    pressTranslation: Float,
    waterLevel: Float,
    pressCount: Int,
    splashValue: Float
) {
    val crackPath = remember { Path() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { translationY = pressTranslation.dp.toPx() }
            .clip(shape = RoundedCornerShape(size = KeyboardDimens.KeyCornerRadius))
            .background(
                color = if (isPressed) {
                    KeyboardColors.KeyPressed
                } else {
                    KeyboardColors.KeyReleased
                }
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = waterLevel)
                .align(alignment = Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            KeyboardColors.WaterGradientStart,
                            KeyboardColors.WaterGradientEnd
                        )
                    )
                )
        )
        CracksEffect(path = crackPath, pressCount = pressCount)
        SplashEffect(value = splashValue)
        Text(
            text = text,
            modifier = Modifier.align(alignment = Alignment.Center),
            fontSize = KeyboardDimens.KeyTextSize,
            fontWeight = FontWeight.Bold,
            color = if (isPressed) {
                KeyboardColors.TextPressed
            } else {
                KeyboardColors.TextReleased
            }
        )
    }
}
