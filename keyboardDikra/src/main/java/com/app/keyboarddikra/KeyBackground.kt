package com.app.keyboarddikra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KeyBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = 1.dp, y = KeyboardDimens.ShadowOffset)
            .background(
                color = KeyboardColors.Shadow,
                shape = RoundedCornerShape(size = KeyboardDimens.KeyCornerRadius)
            )
    )
}
