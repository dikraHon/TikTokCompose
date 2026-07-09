package com.app.keyboarddikra

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardLayout(
    layout: List<List<String>>,
    currentTime: Long,
    lastActivityTime: Long,
    isLandscape: Boolean = false,
    onKeyClick: (String) -> Unit
) {
    val maxKeysInRow = layout.maxOf { it.size }

    layout.forEachIndexed { rowIndex, row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val diff = maxKeysInRow - row.size
            if (diff > 0) Spacer(modifier = Modifier.weight(diff / 2f))

            row.forEachIndexed { colIndex, key ->
                val isIdle = currentTime - lastActivityTime > KeyboardAnimations.IDLE_WAVE_DELAY
                val idleOffset by animateFloatAsState(
                    targetValue = if (isIdle) {
                        val phase = (currentTime - lastActivityTime) / 300f
                        kotlin.math.sin(x = phase + rowIndex * 0.5f + colIndex * 0.3f) * 6f
                    } else 0f,
                    animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessVeryLow),
                    label = "idle_wave"
                )

                KeyButton(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .then(
                            other = if (isLandscape) {
                                Modifier.height(height = 40.dp)
                            } else {
                                Modifier.aspectRatio(ratio = 1f)
                            }
                        )
                        .offset(y = idleOffset.dp)
                        .padding(horizontal = 2.dp, vertical = 2.dp),
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
