package com.app.keyboarddikra

import androidx.compose.animation.core.animateFloatAsState
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
    val colors = LocalKeyboardColors.current
    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            KeyboardAnimations.ACTION_BUTTON_PRESSED_SCALE
        } else {
            1f
        }, label = "scale"
    )

    Box(
        modifier = modifier
            .height(height = KeyboardDimens.ActionButtonHeight)
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
                colors.keyReleased.copy(alpha = 0.7f)
            } else {
                containerColor.copy(
                    alpha = 0.8f
                )
            }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 1.dp, y = KeyboardDimens.ActionButtonShadowY)
                .background(
                    color = colors.actionShadow,
                    shape = RoundedCornerShape(size = KeyboardDimens.ActionButtonCornerRadius)
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = if (isPressed) KeyboardDimens.ActionButtonShadowY.toPx() else 0f
                }
                .background(
                    Brush.verticalGradient(colors = listOf(colors.actionHighlight, baseColor)),
                    shape = RoundedCornerShape(size = KeyboardDimens.ActionButtonCornerRadius)
                )
                .padding(bottom = if (isPressed) 0.dp else KeyboardDimens.ActionButtonShadowY),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                Text(
                    text = text,
                    fontSize = KeyboardDimens.ActionButtonTextSize,
                    color = colors.textReleased,
                    fontWeight = FontWeight.Bold
                )
            } else if (icon != null) {
                icon()
            }
        }
    }
}
