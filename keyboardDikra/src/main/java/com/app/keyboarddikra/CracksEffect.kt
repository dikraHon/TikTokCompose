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

            path.moveTo(x = 0f, y = 0f)
            path.lineTo(x = size.width * 0.2f, y = size.height * 0.3f)
            if (pressCount >= 3) {
                path.lineTo(x = size.width * 0.15f, y = size.height * 0.5f)
                path.lineTo(x = size.width * 0.4f, y = size.height * 0.6f)
            }
            if (pressCount >= 2) {
                path.moveTo(x = size.width, y = size.height)
                path.lineTo(x = size.width * 0.7f, y = size.height * 0.7f)
                if (pressCount >= 4) {
                    path.lineTo(x = size.width * 0.8f, y = size.height * 0.4f)
                    path.lineTo(x = size.width * 0.5f, y = size.height * 0.3f)
                }
            }
            if (pressCount >= 3) {
                path.moveTo(x = 0f, y = size.height * 0.6f)
                path.lineTo(x = size.width * 0.3f, y = size.height * 0.55f)
                path.lineTo(x = size.width * 0.45f, y = size.height * 0.75f)
            }

            drawPath(
                path = path,
                color = colors.crack.copy(alpha = crackAlpha),
                style = Stroke(width = KeyboardDimens.CrackStrokeWidth.toPx())
            )
        }
    }
}
