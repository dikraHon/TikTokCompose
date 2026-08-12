package com.app.keyboarddikra

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun KeyboardLayout(
    layout: List<List<String>>,
    currentTime: Long,
    lastActivityTime: Long,
    isLandscape: Boolean = false,
    onKeyClick: (String) -> Unit
) {
    val maxKeysInRow = layout.maxOf { it.size }
    val isIdle = currentTime - lastActivityTime > KeyboardAnimations.IDLE_WAVE_DELAY
    val rowOffsets = remember(
        key1 = layout.size,
        key2 = isIdle,
        key3 = currentTime
    ) {
        if (isIdle) {
            List(layout.size) { rowIndex ->
                val phase = (currentTime - lastActivityTime) / 300f
                kotlin.math.sin(x = phase + rowIndex * 0.5f) * 6f
            }
        } else {
            List(layout.size) { 0f }
        }
    }

    layout.forEachIndexed { rowIndex, row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KeyboardDimens.RowHorizontalPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val diff = maxKeysInRow - row.size
            if (diff > 0) Spacer(modifier = Modifier.weight(diff / 2f))

            val rowOffset = rowOffsets[rowIndex]

            row.forEachIndexed { colIndex, key ->
                val idleOffset = if (isIdle) {
                    rowOffset + kotlin.math.sin(x = (currentTime - lastActivityTime) / 300f + colIndex * 0.3f) * 2f
                } else 0f
                val animatedOffset by animateFloatAsState(
                    targetValue = idleOffset,
                    animationSpec = spring(
                        dampingRatio = KeyboardAnimations.IDLE_WAVE_DAMPING,
                        stiffness = Spring.StiffnessVeryLow
                    ),
                    label = "idle_wave_$rowIndex$colIndex"
                )

                KeyButton(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .then(
                            other = if (isLandscape) {
                                Modifier.height(height = KeyboardDimens.LandscapeKeyHeight)
                            } else {
                                Modifier.aspectRatio(ratio = 1f)
                            }
                        )
                        .offset { IntOffset(x = 0, y = animatedOffset.dp.toPx().roundToInt()) }
                        .padding(
                            horizontal = KeyboardDimens.KeyPadding,
                            vertical = KeyboardDimens.KeyPadding
                        ),
                    text = key,
                    altText = when (key) {
                        "Е" -> "Ё"
                        "Ь" -> "Ъ"
                        else -> null
                    },
                    onKey = onKeyClick
                )
            }

            if (diff > 0) Spacer(modifier = Modifier.weight(weight = diff / 2f))
        }
    }
}