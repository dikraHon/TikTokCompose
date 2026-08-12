package com.app.keyboarddikra

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KeyboardColors(
    val waterLight: Color,
    val waterMedium: Color,
    val waterDeep: Color,
    val keyPressed: Color,
    val keyReleased: Color,
    val waterGradientStart: Color,
    val waterGradientEnd: Color,
    val textPressed: Color,
    val textReleased: Color,
    val actionDelete: Color,
    val actionConfirm: Color,
    val shadow: Color,
    val splash: Color,
    val crack: Color,
    val actionHighlight: Color,
    val actionShadow: Color,
    val iconTint: Color = Color.White
)

val LightKeyboardColors = KeyboardColors(
    waterLight = Color(color = 0xCCB3E5FC),
    waterMedium = Color(color = 0xCC81D4FA),
    waterDeep = Color(color = 0xCC4FC3F7),
    keyPressed = Color(color = 0xFFE1F5FE),
    keyReleased = Color.White.copy(alpha = 0.85f),
    waterGradientStart = Color(color = 0x4403A9F4),
    waterGradientEnd = Color(color = 0x9901579B),
    textPressed = Color(color = 0xFF0277BD),
    textReleased = Color(color = 0xFF01579B),
    actionDelete = Color(color = 0xFFA5ABB6),
    actionConfirm = Color(color = 0xFF4ADE80),
    shadow = Color(color = 0x50000000),
    splash = Color(color = 0xFF03A9F4),
    crack = Color.Black,
    actionHighlight = Color.White.copy(alpha = 0.4f),
    actionShadow = Color.Black.copy(alpha = 0.2f),
    iconTint = Color.White
)

val DarkKeyboardColors = KeyboardColors(
    waterLight = Color(color = 0xCC01579B),
    waterMedium = Color(color = 0xCC0277BD),
    waterDeep = Color(color = 0xCC039BE5),
    keyPressed = Color(color = 0xFF0d47a1),
    keyReleased = Color(color = 0xFF1e1e1e).copy(alpha = 0.85f),
    waterGradientStart = Color(color = 0x4401579B),
    waterGradientEnd = Color(color = 0x99000000),
    textPressed = Color(color = 0xFFE1F5FE),
    textReleased = Color(color = 0xFFB3E5FC),
    actionDelete = Color(color = 0xFF454a4f),
    actionConfirm = Color(color = 0xFF2e7d32),
    shadow = Color(color = 0x80000000),
    splash = Color(color = 0xFF0288D1),
    crack = Color.White,
    actionHighlight = Color.White.copy(alpha = 0.15f),
    actionShadow = Color.Black.copy(alpha = 0.4f),
    iconTint = Color.White
)

val LocalKeyboardColors = staticCompositionLocalOf { LightKeyboardColors }