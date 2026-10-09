package com.app.haze

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class HazeStyle(
    val blurRadius: Dp = 20.dp,
    val tintColor: Color = Color.White.copy(alpha = 0.15f),
    val borderColor: Color = Color.White.copy(alpha = 0.3f),
    val borderWidth: Dp = 1.dp,
    val shape: Shape = RoundedCornerShape(24.dp)
)

@Composable
fun HazeBox(
    modifier: Modifier = Modifier,
    style: HazeStyle = HazeStyle(),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(style.shape)
            .graphicsLayer {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val blurPx = style.blurRadius.toPx()
                    if (blurPx > 0f) {
                        renderEffect = RenderEffect
                            .createBlurEffect(blurPx, blurPx, Shader.TileMode.MIRROR)
                            .asComposeRenderEffect()
                    }
                }
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        style.tintColor,
                        style.tintColor.copy(alpha = style.tintColor.alpha * 0.5f)
                    )
                ),
                shape = style.shape
            )
            .border(
                width = style.borderWidth,
                brush = Brush.linearGradient(
                    colors = listOf(
                        style.borderColor,
                        style.borderColor.copy(alpha = 0.05f)
                    )
                ),
                shape = style.shape
            ),
        content = content
    )
}
