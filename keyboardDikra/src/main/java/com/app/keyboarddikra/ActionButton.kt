package com.app.keyboarddikra

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    containerColor: Color = Color.White
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            0.97f
        } else {
            1f
        }, label = "scale"
    )

    Box(
        modifier = modifier
            .height(height = 42.dp)
            .scale(scale = scale)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() },
                onLongClick = onLongClick
            )
    ) {
        val baseColor =
            if (containerColor == Color.White) {
                Color.White.copy(alpha = 0.7f)
            } else {
                containerColor.copy(
                    alpha = 0.8f
                )
            }
        val shadowColor = Color(color = 0x33000000)
        val highlightColor = Color.White.copy(alpha = 0.4f)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 1.dp, y = 2.dp)
                .background(
                    color = shadowColor,
                    shape = RoundedCornerShape(size = 8.dp)
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = if (isPressed) 2.dp.toPx() else 0f
                }
                .background(
                    Brush.verticalGradient(colors = listOf(highlightColor, baseColor)),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(bottom = if (isPressed) 0.dp else 2.dp),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color(color = 0xFF01579B),
                    fontWeight = FontWeight.Bold
                )
            } else if (icon != null) {
                icon()
            }
        }
    }
}
