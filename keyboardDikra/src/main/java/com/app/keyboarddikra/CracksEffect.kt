package com.app.keyboarddikra

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CracksEffect(path: Path, pressCount: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (pressCount > 0) {
            path.rewind()
            val crackAlpha = (pressCount * 0.15f).coerceAtMost(maximumValue = 0.6f)

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
                color = Color.Black.copy(alpha = crackAlpha),
                style = Stroke(width = 1.2.dp.toPx())
            )
        }
    }
}
