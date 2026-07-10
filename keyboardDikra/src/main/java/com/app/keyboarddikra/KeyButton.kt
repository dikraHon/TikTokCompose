package com.app.keyboarddikra

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    text: String,
    altText: String? = null,
    onKey: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            KeyboardAnimations.MIN_KEY_SCALE
        } else {
            1f
        },
        animationSpec = tween(
            durationMillis = KeyboardAnimations.PRESS_DURATION,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) {
            KeyboardAnimations.KEY_ROTATION_PRESSED
        } else {
            0f
        },
        animationSpec = tween(durationMillis = KeyboardAnimations.PRESS_DURATION),
        label = "rotation"
    )
    val pressTranslation by animateFloatAsState(
        targetValue = if (isPressed) {
            KeyboardDimens.MaxPressTranslation.value
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = KeyboardAnimations.PRESS_DURATION,
            easing = LowVelocityEasing
        ),
        label = "translation"
    )
    val scope = rememberCoroutineScope()
    var pressCount by remember { mutableIntStateOf(value = 0) }
    val splashAnim = remember { Animatable(initialValue = 0f) }
    val explosionAnim = remember { Animatable(initialValue = 0f) }
    val waterLevel by animateFloatAsState(
        targetValue = pressCount / KeyboardAnimations.MAX_PRESS_COUNT.toFloat(),
        animationSpec = spring(
            dampingRatio = KeyboardAnimations.WATER_SPRING_DAMPING,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "water_level"
    )
    Box(
        modifier = modifier
            .scale(
                if (explosionAnim.value > 0f) {
                    1f + explosionAnim.value
                } else {
                    scale
                }
            )
            .graphicsLayer { rotationZ = rotation }
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    scope.launch {
                        splashAnim.snapTo(targetValue = 0f)
                        splashAnim.animateTo(
                            targetValue = KeyboardAnimations.SPLASH_MAX_VALUE,
                            animationSpec = tween(
                                durationMillis = KeyboardAnimations.SPLASH_DURATION,
                                easing = LinearOutSlowInEasing
                            )
                        )
                    }
                    if (pressCount < KeyboardAnimations.MAX_PRESS_COUNT) {
                        pressCount++
                    }
                    if (pressCount == KeyboardAnimations.MAX_PRESS_COUNT) {
                        scope.launch {
                            explosionAnim.animateTo(
                                targetValue = KeyboardAnimations.EXPLOSION_SCALE,
                                animationSpec = tween(durationMillis = KeyboardAnimations.EXPLOSION_IN_DURATION)
                            )
                            explosionAnim.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = KeyboardAnimations.EXPLOSION_OUT_DURATION)
                            )
                            pressCount = 0
                        }
                    }
                    onKey(text)
                },
                onLongClick = {
                    if (altText != null) {
                        onKey(altText)
                    } else {
                        onKey(text)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        KeyBackground()
        KeyContent(
            text = text,
            isPressed = isPressed,
            pressTranslation = pressTranslation,
            waterLevel = waterLevel,
            pressCount = pressCount,
            splashValue = splashAnim.value
        )
    }
}

private val LowVelocityEasing = Easing { fraction ->
    fraction * fraction * (3 - 2 * fraction)
}
