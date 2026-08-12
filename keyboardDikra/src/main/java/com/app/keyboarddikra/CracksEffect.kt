package com.app.keyboarddikra

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun CracksEffect(path: Path, pressCount: Int) {
    val colors = LocalKeyboardColors.current
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (pressCount > 0) {
            path.rewind()
            val crackAlpha = (pressCount * KeyboardAnimations.CRACK_ALPHA_STEP)
                .coerceAtMost(maximumValue = KeyboardAnimations.CRACK_ALPHA_MAX)
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val minDim = minOf(a = size.width, b = size.height)
            path.moveTo(x = centerX, y = centerY)
            path.lineTo(x = centerX - minDim * 0.3f, y = centerY - minDim * 0.3f)
            if (pressCount >= 2) {
                path.moveTo(x = centerX, y = centerY)
                path.lineTo(x = centerX + minDim * 0.35f, y = centerY - minDim * 0.25f)
            }
            if (pressCount >= 3) {
                path.moveTo(x = centerX, y = centerY)
                path.lineTo(x = centerX - minDim * 0.2f, y = centerY + minDim * 0.35f)
                path.moveTo(x = centerX - minDim * 0.3f, y = centerY - minDim * 0.3f)
                path.lineTo(x = centerX - minDim * 0.45f, y = centerY - minDim * 0.1f)
                path.lineTo(x = centerX - minDim * 0.5f, y = centerY - minDim * 0.2f)
            }

            if (pressCount >= 4) {
                path.moveTo(x = centerX + minDim * 0.35f, y = centerY - minDim * 0.25f)
                path.lineTo(x = centerX + minDim * 0.5f, y = centerY - minDim * 0.1f)
                path.lineTo(x = centerX + minDim * 0.45f, y = centerY + minDim * 0.05f)
            }

            if (pressCount >= 5) {
                path.moveTo(x = centerX - minDim * 0.2f, y = centerY + minDim * 0.35f)
                path.lineTo(x = centerX - minDim * 0.1f, y = centerY + minDim * 0.5f)
                path.lineTo(x = centerX + minDim * 0.1f, y = centerY + minDim * 0.45f)
            }

            drawPath(
                path = path,
                color = colors.crack.copy(alpha = crackAlpha),
                style = Stroke(width = KeyboardDimens.CrackStrokeWidth.toPx())
            )
        }
    }
}