package com.app.keyboarddikra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
@Composable
fun KeyBackground() {
    val colors = LocalKeyboardColors.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = KeyboardDimens.ShadowOffsetX, y = KeyboardDimens.ShadowOffsetY)
            .background(
                color = colors.shadow,
                shape = RoundedCornerShape(size = KeyboardDimens.KeyCornerRadius)
            )
    )
}
